const { test, expect } = require('@playwright/test');
//Funcionales - PARTICIONES DE EQUIVALENCIA: creación de inventario (DLB-6)
// Test de creación de inventario en la aplicación Dolibarr

test.describe('Formulario de Inventario', () => {

  test.beforeEach(async ({ page }) => {
    await page.goto('http://localhost/dolibarr');

    await page.getByRole('textbox', { name: 'Login' }).fill('admin1');
    await page.getByRole('textbox', { name: 'Contraseña' }).click();
    await page.getByRole('textbox', { name: 'Contraseña' }).fill('pass1234');
    await page.getByRole('button', { name: 'Conexión' }).click();
    await page.getByRole('link', { name: '' }).click();
    await page.getByRole('link', { name: ' Inventarios' }).click();
    await page.getByRole('link', { name: 'Nuevo inventario' }).click();
  });

  test('RqId 5.1: Registro válido', async ({ page }) => {
    const randomRef = 'CremaParaPeinar_' + Math.floor(Math.random() * 1000000);
  
    await page.locator('#ref').fill(randomRef);
    await page.locator('#title').fill('Crema para peinar con valor válido');
    await page.locator('#select2-fk_warehouse-container').click();
    await page.getByRole('option', { name: 'CaliSur - Cali Sur, local' }).click();
    await page.locator('#select2-fk_product-container').click();
    await page.getByRole('option', { name: 'CremaPeinar - Crema para' }).click();
    await page.getByRole('button', { name: 'Crear' }).click();
  
    await expect(page).toHaveURL(/\/product\/inventory\/card\.php\?id=\d+/);
    await expect(page.getByText("Inventario creado correctamente")).toBeVisible();
  });

 test('RqId 5.2: Referencia vacía', async ({ page }) => {
    await page.locator('#title').click();
    await page.locator('#title').fill('Crema para peinar ref_vacia');
    await page.locator('#select2-fk_warehouse-container').click();
    await page.getByRole('option', { name: 'CaliSur - Cali Sur, local' }).click();
    await page.locator('#select2-fk_product-container').click();
    await page.getByRole('option', { name: 'CremaPeinar - Crema para' }).click();
    await page.getByRole('button', { name: 'Crear' }).click();
    await expect(page.getByText('El campo \'Ref.\' es obligatorio')).toBeVisible();
  });
  test('RqId 5.3: Campo "Ref." con SQL injection', async ({ page }) => {
    const maliciousRef = "'; DROP TABLE users; --" + Math.floor(Math.random() * 1000000);

    await page.locator('#ref').fill(maliciousRef);
    await page.locator('#title').fill('Crema para peinar con valor malicioso');
    await page.locator('#select2-fk_warehouse-container').click();
    await page.getByRole('option', { name: 'CaliSur - Cali Sur, local' }).click();
    await page.locator('#select2-fk_product-container').click();
    await page.getByRole('option', { name: 'CremaPeinar - Crema para' }).click();
    await page.getByRole('button', { name: 'Crear' }).click();
    await expect(page).toHaveURL(/\/product\/inventory\/card\.php\?id=\d+/);
    //await expect(page.getByText("Inventario creado correctamente")).toBeVisible();
    //await expect(page.getByText("'; DROP TABLE users; --")).toBeVisible();
    page.on('console', msg => {
      expect(msg.type()).not.toBe('error');
    });
    page.on('pageerror', error => {
      throw new Error('Error inesperado: ' + error.message);
    });
  });
  
  test('RqId 5.4: Etiqueta vacía', async ({ page }) => {
    const randomRef = 'CremaParaPeinar_'  + Math.floor(Math.random() * 1000000);
    await page.locator('#ref').fill(randomRef);
    await page.locator('#select2-fk_warehouse-container').click();
    await page.getByRole('option', { name: 'CaliSur - Cali Sur, local' }).click();
    await page.locator('#select2-fk_product-container').click();
    await page.getByRole('option', { name: 'CremaPeinar - Crema para' }).click();
    await page.getByRole('button', { name: 'Crear' }).click();
    await expect(page).not.toHaveURL(/\/product\/inventory\/card\.php\?id=\d+/);
    await expect(page.getByText("El campo 'Etiqueta' es obligatorio")).toBeVisible();
  });
  test('RqId 5.5: Campo "Etiqueta" con SQL injection', async ({ page }) => {
    const maliciousEtiqueda = "'; DROP TABLE users; --";
    const randomRef = 'CremaParaPeinar_' + Math.floor(Math.random() * 1000000);
    await page.locator('#ref').fill(randomRef);
    await page.locator('#title').fill(maliciousEtiqueda);
    await page.locator('#select2-fk_warehouse-container').click();
    await page.getByRole('option', { name: 'CaliSur - Cali Sur, local' }).click();
    await page.locator('#select2-fk_product-container').click();
    await page.getByRole('option', { name: 'CremaPeinar - Crema para' }).click();
    await page.getByRole('button', { name: 'Crear' }).click();
    await expect(page).toHaveURL(/\/product\/inventory\/card\.php\?id=\d+/);
    //await expect(page.getByText("Inventario creado correctamente")).toBeVisible();
    await expect(page.getByText("'; DROP TABLE users; --")).toBeVisible();
    page.on('console', msg => {
      expect(msg.type()).not.toBe('error');
    });
    page.on('pageerror', error => {
      throw new Error('Error inesperado: ' + error.message);
    });
  });
    test('RqId 5.6: Almacen no seleccionada', async ({ page }) => {
    const randomRef = 'AguaSinGasCielo_' + Math.floor(Math.random() * 1000000);
    await page.locator('#ref').fill(randomRef);
    await page.locator('#title').fill('Agua Sin Gas Cielo 1000ml');
    await page.locator('#select2-fk_product-container').click();
    await page.getByRole('option', { name: 'CremaPeinar - Crema para' }).click();
    await page.getByRole('button', { name: 'Crear' }).click();
    await expect(page).not.toHaveURL(/\/product\/inventory\/card\.php\?id=\d+/);
    await expect(page.getByText("El campo 'Almacén' es obligatorio")).toBeVisible();
  });

  test('RqId 5.7: Almacen inválido', async ({ page }) => {
    const randomRef = 'CremaParaPeinar_' + Math.floor(Math.random() * 1000000);
  
    await page.locator('#ref').fill(randomRef);
    await page.locator('#title').fill('Crema para peinar con valor válido');
    await page.locator('#select2-fk_warehouse-container').click();
    await page.getByRole('searchbox').nth(1).fill('AlmacenInexistente');
    await expect(page.getByText('No se han encontrado')).toBeVisible();
    await page.locator('#select2-fk_warehouse-container').click();
    await page.locator('#select2-fk_product-container').click();
    await page.getByRole('option', { name: 'CremaPeinar - Crema para' }).click();
    await page.getByRole('button', { name: 'Crear' }).click();
  
    //await expect(page).not.toHaveURL(/\/product\/inventory\/card\.php\?id=\d+/);
    //await expect(page.getByText('El campo Almacén es obligatorio')).toBeVisible();
  });

  test('RqId 5.8: Producto no seleccionado', async ({ page }) => {
    const randomRef = 'AguaSinGasCielo_' + Math.floor(Math.random() * 1000000);  
    await page.locator('#ref').fill(randomRef);
    await page.locator('#title').fill('Agua Sin Gas Cielo 1000ml');
    await page.locator('#select2-fk_warehouse-container').click();
    await page.getByRole('option', { name: 'CaliSur - Cali Sur, local' }).click();
    await page.getByRole('button', { name: 'Crear' }).click();
  
    await expect(page).not.toHaveURL(/\/product\/inventory\/card\.php\?id=\d+/);
    await expect(page.getByText("El campo 'Producto' es obligatorio")).toBeVisible();
  });

  test('RqId 5.9: Producto inválido', async ({ page }) => {
    const randomRef = 'CremaParaPeinar_' + Math.floor(Math.random() * 1000000);
  
    await page.locator('#ref').fill(randomRef);
    await page.locator('#title').fill('Crema para peinar con valor válido');
    await page.locator('#select2-fk_warehouse-container').click();
    await page.getByRole('option', { name: 'CaliSur - Cali Sur, local' }).click();

   
 
    await page.locator('#select2-fk_product-container').click();
    await page.getByRole('searchbox').nth(1).fill('ProductoInexistente');
    await expect(page.getByText('No se han encontrado')).toBeVisible();
    await page.locator('#select2-fk_product-container').click();
    await page.getByRole('button', { name: 'Crear' }).click();
  
    //await expect(page).not.toHaveURL(/\/product\/inventory\/card\.php\?id=\d+/);
    //await expect(page.getByText('El campo Producto es obligatorio')).toBeVisible();
  });
});
