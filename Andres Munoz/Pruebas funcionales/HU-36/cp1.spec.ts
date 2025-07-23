import { test, expect } from '@playwright/test';

test('CP1 - Eliminación del almacén de manera exitosa', async ({ page }) => {

  await page.goto('https://demo.dolibarr.org/product/index.php?mainmenu=products&leftmenu=');

  // Inicio de sesión
  await page.getByRole('textbox', { name: 'Login' }).click();
  await page.getByRole('textbox', { name: 'Login' }).fill('demo');
  await page.getByRole('textbox', { name: 'Password' }).click();
  await page.getByRole('textbox', { name: 'Password' }).fill('demo');
  await page.getByRole('button', { name: 'Login' }).click();

  // Ir a módulo de almacenes, ver almacenes
  await page.getByRole('link', { name: 'New warehouse' }).click();

  // Crear almacén
  await page.locator('input[name="libelle"]').fill('eliminacion1');
  await page.locator('input[name="lieu"]').fill('estevasereliminado');
  await page.getByRole('button', { name: 'Create' }).click();

  // Eliminar almacén
  await page.getByRole('link', { name: 'Delete' }).click();

  // Confirmar eliminación
  await page.getByRole('button', { name: 'Yes' }).click();

  // Verificar que el almacén ha sido eliminado, redireccionando a la lista de almacenes
  await expect(page.locator('#searchFormList').getByText('Warehouses')).toBeVisible();
  
});