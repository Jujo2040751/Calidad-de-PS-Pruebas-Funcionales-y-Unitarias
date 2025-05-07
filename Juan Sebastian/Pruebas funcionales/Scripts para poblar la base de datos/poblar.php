<?php
require '../main.inc.php';

require_once DOL_DOCUMENT_ROOT.'/product/class/product.class.php';
require_once DOL_DOCUMENT_ROOT.'/product/stock/class/entrepot.class.php';
require_once DOL_DOCUMENT_ROOT.'/societe/class/societe.class.php';
require_once DOL_DOCUMENT_ROOT.'/commande/class/commande.class.php';
require_once DOL_DOCUMENT_ROOT.'/compta/facture/class/facture.class.php';
require_once DOL_DOCUMENT_ROOT.'/product/stock/class/mouvementstock.class.php';
require_once DOL_DOCUMENT_ROOT.'/categories/class/categorie.class.php';

$langs->load("admin");

if (!$user->admin) {
    accessforbidden();
}

echo "<pre>";

// Crear 50 productos con etiquetas
echo "Creando productos...\n";
$productos = [];
for ($i = 1; $i <= 50; $i++) {
    $product = new Product($db);
    $product->ref = 'AUTO_PROD_' . $i;
    $product->label = 'Producto Auto ' . $i;
    $product->type = 0; // Producto
    $product->price = rand(10, 100);
    $product->status = 1;
    $product->status_buy = 1;

    $result = $product->create($user);
    if ($result > 0) {
        echo "Producto $i creado (ID: $result)\n";
        $productos[] = $product;
        
        // Crear categoría (etiqueta)
        $categoria = new Categorie($db);
        $categoria->label = 'AutoEtiqueta ' . $i;
        $categoria->type = Categorie::TYPE_PRODUCT; // Esto evita los warnings
        $categoria->create($user);

        // Asociar producto a categoría
        $sql = "INSERT INTO ".MAIN_DB_PREFIX."categorie_product (idcategorie, idproduct) VALUES (" . $categoria->id . ", " . $product->id . ")";
        $db->query($sql);
    } else {
        echo "Error creando producto $i: " . $product->error . "\n";
    }
}

// Crear 10 servicios
echo "\nCreando servicios...\n";
for ($i = 1; $i <= 10; $i++) {
    $service = new Product($db);
    $service->ref = 'AUTO_SERV_' . $i;
    $service->label = 'Servicio Auto ' . $i;
    $service->type = 1; // Servicio
    $service->price = rand(20, 200);
    $service->status = 1;
    $service->status_buy = 1;

    $res = $service->create($user);
    if ($res > 0) {
        echo "Servicio $i creado (ID: $res)\n";
    } else {
        echo "Error creando servicio $i: " . $service->error . "\n";
    }
}

// Crear 5 almacenes
echo "\nCreando almacenes...\n";
$almacenes = [];
for ($i = 1; $i <= 5; $i++) {
    $warehouse = new Entrepot($db);
    $warehouse->ref = 'AUTO_ALM_' . $i;
    $warehouse->label = 'Almacén Auto ' . $i;
    $warehouse->statut = 1;

    $res = $warehouse->create($user);
    if ($res > 0) {
        echo "Almacén $i creado (ID: $res)\n";
        $almacenes[] = $warehouse;
    } else {
        echo "Error creando almacén $i: " . $warehouse->error . "\n";
    }
}

// Crear 10 clientes
echo "\nCreando clientes...\n";
$clientes = [];
for ($i = 1; $i <= 10; $i++) {
    $client = new Societe($db);
    $client->name = 'AutoCliente ' . $i;
    $client->address = 'Calle Ficticia ' . $i;
    $client->zip = '1000' . $i;
    $client->town = 'Ciudad ' . $i;
    $client->status = 1;

    $res = $client->create($user);
    if ($res > 0) {
        echo "Cliente $i creado (ID: $res)\n";
        $clientes[] = $client;
    } else {
        echo "Error creando cliente $i: " . $client->error . "\n";
    }
}

// Crear 10 proveedores
echo "\nCreando proveedores...\n";
$proveedores = [];
for ($i = 1; $i <= 10; $i++) {
    $provider = new Societe($db);
    $provider->name = 'AutoProveedor ' . $i;
    $provider->address = 'Calle Ficticia ' . $i;
    $provider->zip = '2000' . $i;
    $provider->town = 'Ciudad ' . $i;
    $provider->status = 1;
    $provider->socid = 2; // Definir como proveedor

    $res = $provider->create($user);
    if ($res > 0) {
        echo "Proveedor $i creado (ID: $res)\n";
        $proveedores[] = $provider;
    } else {
        echo "Error creando proveedor $i: " . $provider->error . "\n";
    }
}

// Crear 10 pedidos y facturas
echo "\nCreando pedidos y facturas...\n";
for ($i = 0; $i < 10; $i++) {
    // Crear pedido
    $pedido = new Commande($db);
    $pedido->socid = $clientes[$i % 10]->id;

    // Cargar el producto completo para obtener más detalles como el IVA
    $producto = $productos[$i % 50];
    $producto->fetch($producto->id);

    // Crear el pedido en la base de datos antes de agregar líneas
    $res = $pedido->create($user);

    if ($res > 0) {
        // Añadir línea al pedido
        $desc = $producto->ref . ' - ' . $producto->label;
        $precio = $producto->price;
        $cantidad = 1;
        $iva = isset($producto->tva_tx) ? $producto->tva_tx : 21.0; // Usa 21% por defecto si no está definido

        $resLine = $pedido->addline($desc, $precio, $cantidad, $iva);
        if ($resLine > 0) {
            echo "Pedido $i creado (ID: {$pedido->id}) con línea\n";
        } else {
            echo "Error al agregar línea al pedido $i: " . $pedido->error . "\n";
        }
    } else {
        echo "Error al crear pedido $i: " . $pedido->error . "\n";
    }
}

// Crear inventarios iniciales
echo "\nCreando inventarios...\n";
for ($i = 0; $i < 50; $i++) {
    $producto = $productos[$i];
    $almacen = $almacenes[$i % 5];

    $movement = new MouvementStock($db);
    $res = $movement->reception(
        $user,
        $producto->id,
        $almacen->id,
        10, // cantidad
        0, // precio unitario
        'Carga inicial auto',
        ''
    );

    if ($res > 0) {
        echo "Inventario producto {$producto->ref} en almacén {$almacen->ref}: OK\n";
    } else {
        echo " Error inventario producto {$producto->ref}: " . $movement->error . "\n";
    }
}

echo "Todo completado.";
?>