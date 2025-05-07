import { test, expect } from '@playwright/test';

test('CP32 - Modificar almacén con un fax mayor a 15 caracteres', async ({ page }) => {

  const datos = {
    "referencia": "016AlmacenEquipos",
    "nuevoFax": "+34 914567890123456"
  }

  await page.goto('https://demo.dolibarr.org/product/index.php?mainmenu=products&leftmenu=');

  // Inicio de sesión
  await page.getByRole('textbox', { name: 'Login' }).fill('demo');
  await page.getByRole('textbox', { name: 'Password' }).fill('demo');
  await page.getByRole('button', { name: 'Login' }).click();

  // Ir a módulo de almacenes, ver almacenes
  await page.getByRole('link', { name: 'List' }).nth(2).click();

  // Buscar almacén
  await page.locator('input[name="search_ref"]').click();
  await page.locator('input[name="search_ref"]').fill(datos.referencia);
  await page.getByRole('button', { name: '' }).click();

  // Selecciona el almacén de la lista
  await page.getByRole('link', { name: datos.referencia }).click();

  // Click en el botón de modificar
  await page.getByRole('link', { name: 'Modify' }).click();

  // Modificar almacén
  await page.locator('input[name="fax"]').fill(datos.nuevoFax);

  // Guardar
  await page.getByRole('button', { name: 'Save' }).click();
  
  // Buscar el campo de referencia
  await expect(page.locator('input[name="libelle"]')).toBeVisible();

});