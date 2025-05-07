<?php

use Luracast\Restler\RestException;   
use PHPUnit\Framework\TestCase;

// Simulación de la clase User
class User {
    public function hasRight($right) {
        return $right === 'create_product';
    }
}

// Simulación de la clase Product
class Product {
    private $subproducts;

    public function __construct() {
        $this->subproducts = [
            1 => 'Subproduct 1',
            2 => 'Subproduct 2',
            3 => 'Subproduct 3'
        ];
    }

    public function addSubproduct($subProductId) {
        return isset($this->subproducts[$subProductId]) ? 1 : 0;
    }
}

// Simulación de DolibarrApi
class DolibarrApi {
    public function _checkAccessToResource($user, $resource) {
        return $resource === 'allowed';
    }
}

// Lógica de negocio que queremos probar
class ProductManager {
    private $user;
    private $product;
    private $dolibarrApi;

    public function __construct($user, $product, $dolibarrApi) {
        $this->user = $user;
        $this->product = $product;
        $this->dolibarrApi = $dolibarrApi;
    }

    public function addSubproduct($productId, $subProductId) {
        if (!$this->user->hasRight('create_product')) {
            throw new RestException(403, 'Forbidden');
        }

        if (!$this->dolibarrApi->_checkAccessToResource($this->user, 'allowed')) {
            throw new RestException(403, 'Access Denied');
        }

        $result = $this->product->addSubproduct($subProductId);
        if ($result === 0) {
            throw new RestException(500, 'Failed to add subproduct');
        }

        return $result;
    }
}

class AddProductTest extends TestCase
{
        public function testAddSubproductSuccess()
        {
            $user = $this->createMock(User::class);
            $product = $this->createMock(Product::class);
            $api = $this->createMock(DolibarrApi::class);
    
            $user->method('hasRight')->with('create_product')->willReturn(true);
            $api->method('_checkAccessToResource')->willReturn(true);
            $product->method('addSubproduct')->with(2)->willReturn(1);
    
            $manager = new ProductManager($user, $product, $api);
            $result = $manager->addSubproduct(1, 2);
    
            $this->assertEquals(1, $result);
        }
    
        public function testAddSubproductWithoutPermission()
        {
            $user = $this->createMock(User::class);
            $product = $this->createMock(Product::class);
            $api = $this->createMock(DolibarrApi::class);
    
            $user->method('hasRight')->willReturn(false); // No tiene permisos
    
            $manager = new ProductManager($user, $product, $api);
    
            $this->expectException(RestException::class);
            $this->expectExceptionCode(403);
            $this->expectExceptionMessage('Forbidden');
    
            $manager->addSubproduct(1, 2);
        }
    
        public function testAddSubproductAccessDenied()
        {
            $user = $this->createMock(User::class);
            $product = $this->createMock(Product::class);
            $api = $this->createMock(DolibarrApi::class);
    
            $user->method('hasRight')->willReturn(true);
            $api->method('_checkAccessToResource')->willReturn(false); // No tiene acceso
    
            $manager = new ProductManager($user, $product, $api);
    
            $this->expectException(RestException::class);
            $this->expectExceptionCode(403);
            $this->expectExceptionMessage('Access Denied');
    
            $manager->addSubproduct(1, 2);
        }
    
        public function testAddSubproductFailsInternally()
        {
            $user = $this->createMock(User::class);
            $product = $this->createMock(Product::class);
            $api = $this->createMock(DolibarrApi::class);
    
            $user->method('hasRight')->willReturn(true);
            $api->method('_checkAccessToResource')->willReturn(true);
            $product->method('addSubproduct')->willReturn(0); // Fallo interno
    
            $manager = new ProductManager($user, $product, $api);
    
            $this->expectException(RestException::class);
            $this->expectExceptionCode(500);
            $this->expectExceptionMessage('Failed to add subproduct');
    
            $manager->addSubproduct(1, 99); // ID inválido
        }
    
}
    