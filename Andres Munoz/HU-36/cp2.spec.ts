import { test, expect } from '@playwright/test';

test('CP2 - No eliminación del almacén, mensaje de error', async ({ page }) => {

    await page.goto('https://demo.dolibarr.org/product/index.php?mainmenu=products&leftmenu=');

    // Inicio de sesión
    await page.getByRole('textbox', { name: 'Password' }).click();
    await page.getByRole('textbox', { name: 'Password' }).fill('demo');
    await page.getByRole('button', { name: 'Login' }).click();

    // Ir a módulo de almacenes, ver almacenes
    await page.getByRole('link', { name: 'List' }).nth(2).click();

    // Buscar almacén
    await page.getByRole('row', { name: ' S Germany 6,579,736.76 46,' }).getByRole('link').click();
    await page.getByText('ModifyDelete').click();

    // Eliminar almacén
    await page.getByRole('link', { name: 'Delete' }).click();
    await page.getByRole('button', { name: 'Yes' }).click();
    
    // Verificar que aparece un mensaje de error
    await expect(page.getByText('Cannot delete or update a')).toBeVisible();
  
});