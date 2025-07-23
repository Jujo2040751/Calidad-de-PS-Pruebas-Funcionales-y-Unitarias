<?php

use Luracast\Restler\RestException;
use PHPUnit\Framework\TestCase;

// Mock del usuario con o sin permisos
class UserWarehouseMock {
    public function __construct(private bool $canCreate = true) {}

    public function hasRight($module, $right) {
        return $module === 'stock' && $right === 'creer' && $this->canCreate;
    }
}

// Mock del almacén
class TestWarehouse {
    public $label;
    public $location;
    public $context = [];
    public $id = 123; // ID de prueba
    public $error = "Fallo";
    public $errors = [];

    public function create($user) {
        return 1; // Simula creación exitosa
    }
}

// Acceso simulado global
class DolibarrApiAccess {
    public static $user;
}

// Clase de almacenes con lógica de creación
class Warehouses {
    public static $FIELDS = ['label', 'location'];
    public $warehouse;

    public function __construct() {
        $this->warehouse = new TestWarehouse();
    }

    public function _validate($data) {
        foreach (self::$FIELDS as $field) {
            if (!isset($data[$field])) {
                throw new Exception("Missing field $field");
            }
        }
    }

    public function _checkValForAPI($field, $value, $object) {
        return $value; // Simula verificación de valor
    }

    public function post($request_data = null) {
        if (!DolibarrApiAccess::$user->hasRight('stock', 'creer')) {
            throw new RestException(403);
        }

        $this->_validate($request_data);

        foreach ($request_data as $field => $value) {
            if ($field === 'caller') {
                $this->warehouse->context['caller'] = $this->sanitizeVal($value, 'aZ09');
                continue;
            }

            $this->warehouse->$field = $this->_checkValForAPI($field, $value, $this->warehouse);
        }

        if ($this->warehouse->create(DolibarrApiAccess::$user) < 0) {
            throw new RestException(500, "Error creating warehouse", array_merge([$this->warehouse->error], $this->warehouse->errors));
        }

        return $this->warehouse->id;
    }

    public function sanitizeVal($val, $rule) {
        return preg_replace('/[^a-zA-Z0-9]/', '', $val); // Simplificación de sanitize
    }
}

// Tests para creación de almacenes
class CreateWarehouseTest extends TestCase {

    /** 1. Usuario sin permisos → 403 */
    public function testPostWithoutPermissionThrows403() {
        DolibarrApiAccess::$user = new UserWarehouseMock(false); // sin permisos
        $warehouses = new Warehouses();

        $this->expectException(RestException::class);
        $this->expectExceptionCode(403);

        $warehouses->post(['label' => 'A', 'location' => 'B']);
    }

    /** 2. Usuario con permisos → continúa (200) */
    public function testPostWithPermissionReturnsId() {
        DolibarrApiAccess::$user = new UserWarehouseMock(true);
        $warehouses = new Warehouses();

        $result = $warehouses->post(['label' => 'Central', 'location' => 'Zona 1']);
        $this->assertEquals(123, $result); // 123 es el ID simulado en el mock
    }

    /** 3. Con caller presente → contexto procesado */
    public function testCallerIsSanitizedAndSetInContext() {
        DolibarrApiAccess::$user = new UserWarehouseMock(true);
        $warehouses = new Warehouses();

        $warehouses->post(['label' => 'Depósito', 'location' => 'Zona', 'caller' => 'My_API-Client']);
        $this->assertEquals('MyAPIClient', $warehouses->warehouse->context['caller']);
    }

    /** 4. Sin caller → flujo normal */
    public function testPostWithoutCallerStillSetsFields() {
        DolibarrApiAccess::$user = new UserWarehouseMock(true);
        $warehouses = new Warehouses();

        $warehouses->post(['label' => 'Secundario', 'location' => 'Bodega']);
        $this->assertEquals('Secundario', $warehouses->warehouse->label);
        $this->assertEquals('Bodega', $warehouses->warehouse->location);
    }

    /** 5. Falla la creación del almacén → 500 */
    public function testCreateFailureThrows500() {
        DolibarrApiAccess::$user = new UserWarehouseMock(true);

        $mockWarehouse = $this->createMock(TestWarehouse::class);
        $mockWarehouse->method('create')->willReturn(-1);
        $mockWarehouse->error = "Error grave";
        $mockWarehouse->errors = ["Fallo al guardar"];

        $warehouses = new Warehouses();
        $warehouses->warehouse = $mockWarehouse;

        $this->expectException(RestException::class);
        $this->expectExceptionCode(500);
        $this->expectExceptionMessage("Error creating warehouse");

        $warehouses->post(['label' => 'Principal', 'location' => 'Este']);
    }

    /** 6. Creación exitosa → retorna ID del almacén */
    public function testCreateSuccessReturnsWarehouseId() {
        DolibarrApiAccess::$user = new UserWarehouseMock(true);
        $warehouses = new Warehouses();

        $result = $warehouses->post(['label' => 'Nuevo', 'location' => 'Norte']);
        $this->assertEquals(123, $result);
    }
}
