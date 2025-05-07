<?php
require '../main.inc.php';
require_once DOL_DOCUMENT_ROOT.'/product/class/product.class.php';
require_once DOL_DOCUMENT_ROOT.'/categories/class/categorie.class.php';

if (!$user->admin) {
    accessforbidden();
}

echo "<pre>";
echo "Asignando 20 productos aleatorios a la categoría AutoEtiqueta 1...\n";

// Cargar la categoría AutoEtiqueta 1
$categoria = new Categorie($db);
if ($categoria->fetch(1) > 0) {
    // Obtener todos los productos disponibles
    $sql = "SELECT rowid FROM ".MAIN_DB_PREFIX."product WHERE fk_product_type = 0 ORDER BY RAND() LIMIT 20";
    $resql = $db->query($sql);

    if ($resql) {
        $num = $db->num_rows($resql);
        if ($num === 0) {
            echo "❌ No se encontraron productos para asignar.\n";
        } else {
            $asignados = 0;
            while ($obj = $db->fetch_object($resql)) {
                $product = new Product($db);
                if ($product->fetch($obj->rowid) > 0) {
                    $result = $categoria->add_type($product, 'product');
                    if ($result > 0) {
                        echo "Producto {$product->ref} asignado a AutoEtiqueta 1\n";
                        $asignados++;
                    } else {
                        echo "Error asignando producto {$product->ref}: " . $categoria->error . "\n";
                    }
                }
            }

            echo "Total productos asignados: $asignados\n";
        }
    } else {
        echo "Error en la consulta SQL: " . $db->lasterror() . "\n";
    }
} else {
    echo "No se encontró la categoría AutoEtiqueta 1.\n";
}

echo "Proceso completado.";