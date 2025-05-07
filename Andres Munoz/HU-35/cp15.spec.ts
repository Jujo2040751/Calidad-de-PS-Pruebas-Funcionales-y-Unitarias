import { test, expect } from '@playwright/test';

test('CP15 - Modificar almacén con descripción mayor a 255 caracteres', async ({ page }) => {

  const datos = {
    "referencia": "007AlmacenDescripcionCorta",
    "nuevaDescripcion": "Este almacén tiene una descripción demasiado extensa. Fue construido en 1995, renovado en 2020, y cuenta con más de 5000 m² de espacio, zonas de carga automatizadas, paneles solares, sistema de gestión por RFID, áreas de picking, oficinas y comedor interno para el personal."
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
  await page.locator('iframe[title="Editor\\, desc"]').contentFrame().locator('body').fill(datos.nuevaDescripcion);

  // Guardar
  await page.getByRole('button', { name: 'Save' }).click();
  
  // Buscar el campo de referencia
  await expect(page.locator('input[name="libelle"]')).toBeVisible();

});