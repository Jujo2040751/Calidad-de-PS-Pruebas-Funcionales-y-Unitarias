<?php
require '../main.inc.php';
require_once DOL_DOCUMENT_ROOT.'/categories/class/categorie.class.php';

if (!$user->admin) {
    accessforbidden();
}

echo "<pre>";
echo "Asignando subcategorías (AutoEtiqueta 30 a 50) a AutoEtiqueta 1...\n";

// Buscar la categoría padre (AutoEtiqueta 1)
$categoriaPadre = new Categorie($db);
$found = $categoriaPadre->fetch(1); // ID 1 (asumiendo que es AutoEtiqueta 1)

if ($found > 0) {
    for ($i = 30; $i <= 50; $i++) {
        $subcat = new Categorie($db);
        if ($subcat->fetch($i) > 0) {
            // Actualizar fk_parent directamente
            $subcat->fk_parent = $categoriaPadre->id;
            $res = $subcat->update($user);
            if ($res > 0) {
                echo "Subcategoría {$i} asignada a AutoEtiqueta 1\n";
            } else {
                echo "Error al asignar la subcategoría {$i}: " . $subcat->error . "\n";
            }
        } else {
            echo "No se encontró la subcategoría {$i}\n";
        }
    }
} else {
    echo "No se encontró la categoría AutoEtiqueta 1.\n";
}

echo "Proceso completado.";