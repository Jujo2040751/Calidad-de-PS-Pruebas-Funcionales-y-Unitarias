import { test, expect } from '@playwright/test';

test('CP3 - No confirma eliminación del almacén, intenta repetir el proceso', async ({ page }) => {

  await page.goto('https://demo.dolibarr.org/product/index.php?mainmenu=products&leftmenu=');

  // Inicio de sesión
  await page.getByRole('textbox', { name: 'Login' }).click();
  await page.getByRole('textbox', { name: 'Login' }).fill('demo');
  await page.getByRole('textbox', { name: 'Password' }).click();
  await page.getByRole('textbox', { name: 'Password' }).fill('demo');
  await page.getByRole('button', { name: 'Login' }).click();

  // Ir a módulo de almacenes, crar almacén
  await page.getByRole('link', { name: 'New warehouse' }).click();

  // Crear almacén
  await page.locator('input[name="libelle"]').fill('eliminacion2');
  await page.locator('input[name="lieu"]').fill('esteTambienVaSerEliminado');

  // Guardar
  await page.getByRole('button', { name: 'Create' }).click();

  // Eliminar almacén
  await page.getByRole('link', { name: 'Delete' }).click();

  // No confirmar eliminación
  await page.getByRole('button', { name: 'No' }).click();

  // Verifica si es posible seleccionar de nuevo el boton eliminar
  expect(page.locator('#id-right').getByRole('link', { name: 'Delete' })).toBeVisible();

});