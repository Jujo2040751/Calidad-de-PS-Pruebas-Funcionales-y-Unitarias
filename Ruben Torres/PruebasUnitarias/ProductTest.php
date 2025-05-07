<?php

use Luracast\Restler\RestException;   
use PHPUnit\Framework\TestCase;

class User {
    public function hasRight($right) {
        if ($right == 'create_product') {
            return true; // Simulamos que el usuario tiene permisos
        }
        return false; // Simulamos que no tiene permisos en otros casos
    }
}

class Product
{
    private $subproducts;

    public function __construct()
    {
        $this->subproducts = [
            1 => 'Subproduct 1',
            2 => 'Subproduct 2',
            3 => 'Subproduct 3'
        ];
    }

    // Método simulado para eliminar subproducto
    public function delSubproducts($subProductId)
    {
        if (isset($this->subproducts[$subProductId])) {
            unset($this->subproducts[$subProductId]);
            return 1; // Retorna 1 para indicar éxito
        }
        return 0; // Retorna 0 si no se pudo eliminar
    }
}

class DolibarrApi
{
    public function _checkAccessToResource($user, $resource)
    {
        if ($resource === 'allowed') {
            return true; // Acceso permitido
        }

        return false; // Acceso denegado
    }

    public function someOtherMethod()
    {
        return 'Some result';
    }
}

class ProductClass
{
    private $user;
    private $product;
    private $dolibarrApi;

    public function __construct($user, $product, $dolibarrApi)
    {
        $this->user = $user;
        $this->product = $product;
        $this->dolibarrApi = $dolibarrApi;
    }

    public function delSubproducts($productId, $subProductId)
    {
        if (!$this->user->hasRight('create_product')) {
            throw new RestException(403, 'Forbidden');
        }

        if (!$this->dolibarrApi->_checkAccessToResource($this->user, 'allowed')) {
            throw new RestException(403, 'Access Denied');
        }

        return $this->product->delSubproducts($subProductId); // Llamamos al método de Product
    }

    public function getProduct($id)
    {
        return new Product();
    }
}

class ProductTest extends TestCase
{
    public function testDelSubproducts()
    {
        $userMock = $this->createMock(User::class);
        $productMock = $this->createMock(Product::class);
        $dolibarrApiMock = $this->createMock(DolibarrApi::class);

        // Simulamos los permisos
        $userMock->method('hasRight')->willReturn(true);

        // Simulamos la verificación de acceso
        $dolibarrApiMock->method('_checkAccessToResource')->willReturn(true);

        // Simulamos el método del producto
        $productMock->method('delSubproducts')->willReturn(1); // Simula éxito en la eliminación

        $productClassMock = $this->getMockBuilder(ProductClass::class)
            ->setConstructorArgs([$userMock, $productMock, $dolibarrApiMock])
            ->onlyMethods(['getProduct'])
            ->getMock();

        $productClassMock->method('getProduct')->willReturn($productMock);

        // Llamamos al método de eliminación
        $result = $productClassMock->delSubproducts(1, 2); // Elimina el subproducto con ID 2

        $this->assertEquals(1, $result); // Verificamos que el resultado sea 1 (éxito)
    }

    public function testDelSubproductsWithoutPermission()
    {
        $userMock = $this->createMock(User::class);
        $productMock = $this->createMock(Product::class);
        $dolibarrApiMock = $this->createMock(DolibarrApi::class);

        $userMock->method('hasRight')->willReturn(false); // Usuario sin permisos

        $productClassMock = $this->getMockBuilder(ProductClass::class)
            ->setConstructorArgs([$userMock, $productMock, $dolibarrApiMock])
            ->onlyMethods(['getProduct'])
            ->getMock();

        $this->expectException(RestException::class);
        $this->expectExceptionCode(403);

        $productClassMock->delSubproducts(1, 2); // Intentamos eliminar sin permisos
    }

    public function testDelSubproductsAccessDenied()
    {
        $userMock = $this->createMock(User::class);
        $productMock = $this->createMock(Product::class);
        $dolibarrApiMock = $this->createMock(DolibarrApi::class);

        $userMock->method('hasRight')->willReturn(true);
        $dolibarrApiMock->method('_checkAccessToResource')->willReturn(false); // Acceso denegado

        $productClassMock = $this->getMockBuilder(ProductClass::class)
            ->setConstructorArgs([$userMock, $productMock, $dolibarrApiMock])
            ->onlyMethods(['getProduct'])
            ->getMock();

        $this->expectException(RestException::class);
        $this->expectExceptionCode(403);

        $productClassMock->delSubproducts(1, 2); // Intentamos eliminar pero el acceso está denegado
    }
}
