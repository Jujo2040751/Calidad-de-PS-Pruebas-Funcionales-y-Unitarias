<?php
use PHPUnit\Framework\TestCase;

if (!class_exists('Luracast\Restler\RestException')) {
	class RestException extends Exception
	{
		public function __construct($code = 500, $message = '', $data = [])
		{
			parent::__construct($message, $code);
		}
	}
}


class UserMock
{
	public $rights;
	public $login = 'admin';

	public function __construct($canDelete = true)
	{
		$this->rights = (object) [
			'produit' => (object) [
				'supprimer' => $canDelete
			]
		];
	}
}

class ProductMock
{
	public $id;

	public function fetch($id)
	{
		if ($id === 999)
			return false; // Simula producto no encontrado
		$this->id = $id;
		return true;
	}

	public function delete($user)
	{
		if ($this->id === 1)
			return -1;  // Error interno
		if ($this->id === 2)
			return 0;   // Producto en uso
		return 1; // Eliminación exitosa
	}
}

class DolibarrApiAccess
{
	public static $user;
}

class DolibarrApi
{
	public static function _checkAccessToResource($type, $id)
	{
		if ($id === 3)
			return false;
		return true;
	}
}

class Products
{
	public $product;

	public function __construct()
	{
		$this->product = new ProductMock();
	}

	public function delete($id)
	{
		if (!DolibarrApiAccess::$user->rights->produit->supprimer) {
			throw new RestException(403);
		}
		$result = $this->product->fetch($id);
		if (!$result) {
			throw new RestException(404, 'Product not found');
		}

		if (!DolibarrApi::_checkAccessToResource('product', $this->product->id)) {
			throw new RestException(403, 'Access not allowed for login ' . DolibarrApiAccess::$user->login);
		}

		global $user;
		$user = DolibarrApiAccess::$user;

		$res = $this->product->delete($user);
		if ($res < 0) {
			throw new RestException(500, "Can't delete, error occurs");
		} elseif ($res == 0) {
			throw new RestException(409, "Can't delete, that product is probably used");
		}

		return [
			'success' => [
				'code' => 200,
				'message' => 'Object deleted'
			]
		];
	}
}

class ApiProductTest extends TestCase
{
	//Eliminación exitosa (A, B, D, F, H, J, L, M, N, Ñ, O )
	public function testDeleteSuccessReturnsMessage()
	{
		DolibarrApiAccess::$user = new UserMock(canDelete: true);
		$products = new Products();

		$result = $products->delete(5);
		$this->assertEquals(200, $result['success']['code']);
		$this->assertEquals('Object deleted', $result['success']['message']);
	}

	//Eliminación retorna 0 (A, B, D, F, H, K, L, M, N, Ñ, O)
	public function testDeleteUsedProductThrows409()
	{
		DolibarrApiAccess::$user = new UserMock(canDelete: true);
		$products = new Products();

		$this->expectException(RestException::class);
		$this->expectExceptionCode(409);

		$products->delete(2);
	}
	//Eliminación retorna >0 (A, B, D, F, I, M, N, Ñ, O) 
	public function testDeleteInternalErrorThrows500()
	{
		DolibarrApiAccess::$user = new UserMock(canDelete: true);
		$products = new Products();

		$this->expectException(RestException::class);
		$this->expectExceptionCode(500);

		$products->delete(1);
	}

	//Usuario sin permisos para eliminar este producto (A, B, D, G, N, Ñ, O)
	public function testDeleteAccessDeniedThrows403()
	{
		DolibarrApiAccess::$user = new UserMock(canDelete: true);
		$products = new Products();

		$this->expectException(RestException::class);
		$this->expectExceptionCode(403);

		$products->delete(3);
	}

	//Producto inexistente (A, B, E, Ñ, O)
	public function testDeleteNotFoundThrows404()
	{
		DolibarrApiAccess::$user = new UserMock(canDelete: true);
		$products = new Products();

		$this->expectException(RestException::class);
		$this->expectExceptionCode(404);

		$products->delete(999);
	}

	//Usuario sin permisos de eliminación (A, C, O)
	public function testDeleteWithoutPermissionThrows403()
	{
		DolibarrApiAccess::$user = new UserMock(canDelete: false);
		$products = new Products();

		$this->expectException(RestException::class);
		$this->expectExceptionCode(403);

		$products->delete(10);
	}


}
