<?php

use Luracast\Restler\RestException;
use PHPUnit\Framework\TestCase;
// require_once 'mock/DolibarrApiMock.php';
// require_once 'mock/DolibarrApiAccessMock.php';

// Mock de la clase de usuario con permisos 
class UserMock {
    public $rights;

    public function __construct($canCreate = true) {
        $this->rights = (object)[
            'categorie' => (object)[
                'creer' => $canCreate
            ]
        ];
    }
}

// Clase Categories y dependencias simuladas 
class TestCategorie {
    public $label;
    public $type;
    public $context = [];

    public function create($user) {
        return 1;
    }
}

class DolibarrApiAccess {
    public static $user;
}

class Categories {
    public static $FIELDS = ['label', 'type'];
    public $category;

    public function __construct() {
        $this->category = new TestCategorie();
    }

    public function _validate($data) {
        foreach (self::$FIELDS as $field) {
            if (!isset($data[$field])) {
                throw new Exception("Missing field $field");
            }
        }
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
}

class CreateCategorieTest extends TestCase {
    
    /** 1. Usuario sin permisos → 403 */
    public function testPostWithoutPermissionThrows403() {
        DolibarrApiAccess::$user = new UserMock(false); // sin permisos
        $categories = new Categories();

        $this->expectException(Exception::class);
        $this->expectExceptionMessage("Forbidden");
        $this->expectExceptionCode(403);

        $categories->post(['label' => 'A', 'type' => 'B']);
    }


    /** 2. Usuario con permisos → continúa (200) */
    public function testPostWithPermissionContinues() {
        DolibarrApiAccess::$user = new UserMock(true); // con permisos
        $categories = new Categories();

        $result = $categories->post(['label' => 'Test', 'type' => '1']);
        $this->assertEquals(1, $result);
    }


     /** 3. Con caller presente → contexto procesado (200) */
    public function testCallerIsSetInContext() {
        DolibarrApiAccess::$user = new UserMock(true);
        $categories = new Categories();

        $categories->post(['label' => 'Label', 'type' => '1', 'caller' => 'API']);
        $this->assertEquals('API', $categories->category->context['caller']);
    }


    /** 4. Sin caller → flujo normal (200) */
    public function testPostWithoutCallerStillSetsFields() {
        DolibarrApiAccess::$user = new UserMock(true);
        $categories = new Categories();

        $categories->post(['label' => 'Label', 'type' => '1']);
        $this->assertEquals('Label', $categories->category->label);
        $this->assertEquals('1', $categories->category->type);
    }

    /** 5. Falla la creacion de la etiqueta */
    public function testCreateFailureThrows500() {
        DolibarrApiAccess::$user = new UserMock(true);
        
        $mockCategory = $this->createMock(TestCategorie::class);
        $mockCategory->method('create')->willReturn(-1); // fuerza fallo

        $categories = new Categories();
        $categories->category = $mockCategory;

        $this->expectException(Exception::class);
        $this->expectExceptionMessage("Error creating category");
        $this->expectExceptionCode(500);

        $categories->post(['label' => 'Label', 'type' => '1']);
    }


    /** 6. Creación exitosa → retorna ID */
    public function testCreateSuccessReturnsId() {
        DolibarrApiAccess::$user = new UserMock(true);
        $categories = new Categories();

        $result = $categories->post(['label' => 'Label', 'type' => '1']);
        $this->assertEquals(1, $result);
    }
}