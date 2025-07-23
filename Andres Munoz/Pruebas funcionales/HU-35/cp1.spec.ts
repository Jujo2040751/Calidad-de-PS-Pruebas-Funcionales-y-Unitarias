import { test, expect } from '@playwright/test';

test('CP1 - Modificar referencia del almacén con referencia válida', async ({ page }) => {

  const datos = {
    "referencia": "003AlmacenEste",
    "nuevaReferencia": "003AlmacenEsteMod"
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
  await page.locator('input[name="libelle"]').fill(datos.nuevaReferencia);

  // Guardar
  await page.getByRole('button', { name: 'Save' }).click();
  
  // Buscar el campo de referencia
  await expect(page.locator('input[name="libelle"]')).not.toBeVisible();

});