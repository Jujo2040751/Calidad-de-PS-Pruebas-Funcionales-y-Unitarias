import { test, expect } from '@playwright/test';
// Funcionales - PARTICIONES DE EQUIVALENCIA: listado de inventarios (DLB-7)

test.describe('Listado de Inventarios - Particiones de Equivalencia', () => {

  test.beforeEach(async ({ page }) => {
    await page.goto('http://localhost/dolibarr/');
    await page.getByRole('textbox', { name: 'Login' }).fill('admin1');
    await page.getByRole('textbox', { name: 'Contraseña' }).click();
    await page.getByRole('textbox', { name: 'Contraseña' }).fill('pass1234');
    await page.getByRole('button', { name: 'Conexión' }).click();
    await page.getByRole('link', { name: 'Productos | Servicios' }).click();
    await page.getByRole('link', { name: ' Inventarios' }).click();
  });

  test('RqId 6.1. Visualización de columnas por defecto', async ({ page }) => {
    await expect(page.getByRole('cell', { name: 'Ref.' })).toBeVisible();
    await expect(page.getByRole('cell', { name: 'Etiqueta' })).toBeVisible();
    await expect(page.getByRole('cell', { name: 'Depósito' })).toBeVisible();
    await expect(page.getByRole('cell', { name: 'Producto' })).toBeVisible();
    await expect(page.getByRole('cell', { name: 'Estado' })).toBeVisible();
  });

  test('RqId 6.2. Búsqueda con referencia existente', async ({ page }) => {
    await page.locator('input[name="search_ref"]').fill('CremPeiSav');
    await page.getByRole('button', { name: '' }).click();
    await expect(page.getByRole('link', { name: ' CremPeiSav', exact: true })).toBeVisible()
  });
  

  test('RqId 6.3. Búsqueda con referencia inexistente', async ({ page }) => {
    await page.locator('input[name="search_ref"]').fill('fwefwklafqf_1421');
    await page.locator('input[name="search_ref"]').press('Enter');
    await page.getByRole('button', { name: '' }).click();
    await expect(page.getByText("No se encontraron registros")).toBeVisible();
  });

  test('RqId 6.4. Cambio a vista Kanban', async ({ page }) => {
    await page.getByRole('link', { name: '' }).click();
    await expect(page.locator('.trkanban')).toBeVisible(); 
  });

  test('RqId 6.5: Cambiar a paginación de 50 elementos', async ({ page }) => {
    await page.getByRole('combobox', { name: '20' }).locator('span').nth(1).click();
    await page.getByRole('option', { name: '50', exact: true }).click();
    const filas = page.locator('table tbody tr');
    const count = await filas.count();
    expect(count).toBeGreaterThan(1);
  });
  
});
