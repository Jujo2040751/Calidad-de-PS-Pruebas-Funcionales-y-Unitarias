<?php

use PHPUnit\Framework\TestCase;

class RestException extends Exception
{
    public function __construct($message, $code = 0, Exception $previous = null)
    {
        parent::__construct($message, $code, $previous);
    }
}

class UserMock {
    public $rights;
    public $login = 'testuser';
    
    public function __construct($canCreate = true, $canDelete = true) {
        $this->rights = (object)[
            'categorie' => (object)[
                'creer' => $canCreate,
                'supprimer' => $canDelete
            ]
        ];
    }
    
    public function hasRight($module, $action) {
        if ($module === 'categorie') {
            if ($action === 'creer') return $this->rights->categorie->creer;
            if ($action === 'supprimer') return $this->rights->categorie->supprimer;
        }
        return false;
    }
}

class DolibarrApiAccess {
    public static $user;
}

class FakeCategorie {
    public $label;
    public $type;
    public $id;
    public $context = [];
    
    public function create($user) {
        if ($this->label === 'fail') return -1;
        $this->id = 1;
        return 1;
    }
    
    public function fetch($id) {
        if ($id == 1) {
            $this->id = $id;
            $this->label = 'Test Category';
            $this->type = 'Test Type';
            return true;
        }
        return false;
    }
    
    public function update($user) {
        if ($this->label === 'fail') return -1;
        return 1;
    }
    
    public function delete($user) {
        return 1; // Default to success
    }
}

class Categories {
    public static $FIELDS = ['label', 'type'];
    public $category;

    public function __construct() {
        $this->category = new FakeCategorie();
    }

    public function _validate($data) {
        foreach (self::$FIELDS as $field) {
            if (!isset($data[$field])) {
                throw new Exception("Missing field $field");
            }
        }
    }

    public function get($id) {
        if ($this->category->fetch($id)) {
            return $this->category;
        }
        throw new Exception("Category not found", 404);
    }

    public function post($request_data = null) {
        if (!DolibarrApiAccess::$user->rights->categorie->creer) {
            throw new Exception("Forbidden", 403);
        }

        $this->_validate($request_data);

        foreach ($request_data as $field => $value) {
            if ($field === 'caller') {
                $this->category->context['caller'] = $value;
                continue;
            }
            $this->category->$field = $value;
        }

        if ($this->category->create(DolibarrApiAccess::$user) < 0) {
            throw new Exception("Error creating category", 500);
        }

        return 1;
    }

    public function put($id, $request_data = null) {
        if (!DolibarrApiAccess::$user->rights->categorie->creer) {
            throw new Exception("Forbidden", 403);
        }

        if (!$this->category->fetch($id)) {
            throw new Exception("Category not found", 404);
        }

        foreach ($request_data as $field => $value) {
            if ($field == 'id') continue;
            if ($field === 'caller') {
                $this->category->context['caller'] = $value;
                continue;
            }
            $this->category->$field = $value;
        }

        if ($this->category->update(DolibarrApiAccess::$user) <= 0) {
            throw new Exception("Error updating category", 500);
        }
        if (!$this->checkResourceAccess('categorie', $id)) {
            throw new Exception("Access not allowed for user", 403);
        }

        return $this->get($id);
    }

    public function delete($id) {
        if (!DolibarrApiAccess::$user->rights->categorie->supprimer) {
            throw new Exception("Forbidden", 403);
        }

        if (!$this->category->fetch($id)) {
            throw new Exception("Category not found", 404);
        }

        if ($this->category->delete(DolibarrApiAccess::$user) <= 0) {
            throw new Exception("Error deleting category", 500);
        }
        if (!$this->checkResourceAccess('categorie', $id)) {
            throw new Exception("Access not allowed for user", 403);
        }

        return [
            'success' => [
                'code' => 200,
                'message' => 'Category deleted'
            ]
        ];
    }
    private static $accessCheckCallback = null;

    public static function setAccessCheckCallback(?callable $callback = null) {
        self::$accessCheckCallback = $callback;
    }

    private function checkResourceAccess($resourceType, $resourceId) {
        if (self::$accessCheckCallback !== null) {
            return (bool)call_user_func(self::$accessCheckCallback, $resourceType, $resourceId);
        }
        // Default to true when no callback set
        return true;
    }
}

class DolibarrApi {
    public static function _checkAccessToResource($resourceType, $resourceId) {
        return true;
    }
}

class CategoriesApiTest extends TestCase
{
    public function testPostFailsIfUserHasNoRight()
    {
        DolibarrApiAccess::$user = new UserMock(false);
        $categories = new Categories();

        $this->expectException(Exception::class);
        $this->expectExceptionMessage("Forbidden");
        $this->expectExceptionCode(403);

        $categories->post(['label' => 'x', 'type' => '1']);
    }

    public function testPostSuccess()
    {
        DolibarrApiAccess::$user = new UserMock(true);
        $categories = new Categories();

        $result = $categories->post(['label' => 'x', 'type' => '1']);
        $this->assertEquals(1, $result);
    }

    public function testPostFailsOnCreateError()
    {
        DolibarrApiAccess::$user = new UserMock(true);

        $mockCategory = $this->createMock(FakeCategorie::class);
        $mockCategory->method('create')->willReturn(-1);

        $categories = new Categories();
        $categories->category = $mockCategory;

        $this->expectException(Exception::class);
        $this->expectExceptionMessage("Error creating category");
        $this->expectExceptionCode(500);

        $categories->post(['label' => 'x', 'type' => '1']);
    }

    public function testPutFailsIfNoRight()
    {
        DolibarrApiAccess::$user = new UserMock(false);
        $categories = new Categories();

        $this->expectException(Exception::class);
        $this->expectExceptionCode(403);

        $categories->put(1, ['label' => 'Updated']);
    }

    public function testPutFailsIfCategoryNotFound()
    {
        DolibarrApiAccess::$user = new UserMock(true);
        $categories = new Categories();

        $this->expectException(Exception::class);
        $this->expectExceptionCode(404);

        $categories->put(999, ['label' => 'Updated']);
    }

    public function testPutFailsIfAccessDenied()
{
    DolibarrApiAccess::$user = new UserMock(true);
    $categories = new Categories();  // Add this line
    
    Categories::setAccessCheckCallback(function() { return false; });
    
    $this->expectException(Exception::class);
    $categories->put(1, ['label' => 'Updated']);
}

public function testPutSuccess()
{
    DolibarrApiAccess::$user = new UserMock(true);
    $categories = new Categories();
    
    // Ensure access is allowed for success test
    Categories::setAccessCheckCallback(function() { return true; });
    
    $result = $categories->put(1, ['label' => 'Updated', 'caller' => 'API']);
    $this->assertEquals(1, $result->id);
    $this->assertEquals('API', $result->context['caller']);
}


    public function testPutFailsOnUpdate()
    {
        DolibarrApiAccess::$user = new UserMock(true);
        $categories = new Categories();
        $categories->category->label = 'fail';

        $this->expectException(Exception::class);
        $this->expectExceptionCode(500);

        $categories->put(1, ['label' => 'fail']);
    }

    public function testDeleteFailsIfNoRight()
    {
        DolibarrApiAccess::$user = new UserMock(true, false); // no delete right
        $categories = new Categories();

        $this->expectException(Exception::class);
        $this->expectExceptionCode(403);

        $categories->delete(1);
    }

    public function testDeleteFailsIfNotFound()
    {
        DolibarrApiAccess::$user = new UserMock(true, true);
        $categories = new Categories();

        $this->expectException(Exception::class);
        $this->expectExceptionCode(404);

        $categories->delete(999);
    }

    public function testDeleteFailsIfAccessDenied()
{
    DolibarrApiAccess::$user = new UserMock(true, true);
    $categories = new Categories();  // Add this line
    
    Categories::setAccessCheckCallback(function() { return false; });
    
    $this->expectException(Exception::class);
    $categories->delete(1);
}

public function testDeleteFailsIfDeleteFails()
{
    DolibarrApiAccess::$user = new UserMock(true, true);
    
    // Create a mock category that will fail on delete
    $mockCategory = $this->createMock(FakeCategorie::class);
    $mockCategory->method('fetch')->willReturn(true);
    $mockCategory->method('delete')->willReturn(0); // Simulate failure
    
    $categories = new Categories();
    $categories->category = $mockCategory;

    $this->expectException(Exception::class);
    $this->expectExceptionCode(500);

    $categories->delete(1);
}

public function testDeleteSuccess()
{
    DolibarrApiAccess::$user = new UserMock(true, true);
    $categories = new Categories();
    
    // Ensure access is allowed for success test
    Categories::setAccessCheckCallback(function() { return true; });
    
    $result = $categories->delete(1);
    $this->assertEquals(200, $result['success']['code']);
    $this->assertEquals('Category deleted', $result['success']['message']);
}
protected function tearDown(): void
{
    // Reset the access check callback after each test
    Categories::setAccessCheckCallback(null);
    parent::tearDown();
}
}