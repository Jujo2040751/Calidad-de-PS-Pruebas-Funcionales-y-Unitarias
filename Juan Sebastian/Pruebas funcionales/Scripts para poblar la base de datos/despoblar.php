<?php
$host = 'localhost';
$dbname = 'dolibarr';
$user = 'root';
$pass = '';  // cámbialo si es necesario

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $user, $pass);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $pdo->exec("SET FOREIGN_KEY_CHECKS = 0");

    $tablasParaDespoblar = [
        'llx_stock_mouvement',
        'llx_commandedet',
        'llx_commande',
        'llx_categorie_product',
        'llx_categorie',
        'llx_product',
        'llx_entrepot',
        'llx_societe'
    ];

    foreach ($tablasParaDespoblar as $tabla) {
        $pdo->exec("TRUNCATE TABLE `$tabla`");
    }

    $pdo->exec("SET FOREIGN_KEY_CHECKS = 1");

    echo "Tablas despobladas correctamente.";

} catch (PDOException $e) {
    echo " Error: " . $e->getMessage();
}
?>