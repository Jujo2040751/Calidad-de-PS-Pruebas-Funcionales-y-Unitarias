import { test, expect } from '@playwright/test';
// Funcionales - VALORES LÍMITE: modificación masiva de precio (DLB-10)

test.describe('Actualización masiva del precio', () => {
    test.beforeEach(async ({ page }) => {
        await page.goto('http://localhost/dolibarr/');
        await page.getByRole('textbox', { name: 'Login' }).fill('admin1');
        await page.getByRole('textbox', { name: 'Contraseña' }).fill('pass1234');
        await page.getByRole('button', { name: 'Conexión' }).click();
        await page.getByRole('link', { name: 'Productos | Servicios' }).click();
        await page.getByRole('link', { name: 'Listado' }).first().click();
        await page.getByRole('row', { name: ' CremaPeinar Crema para' }).getByRole('checkbox').check();
        await page.getByRole('textbox', { name: '-- Seleccione la acción --' }).click();
        await page.getByRole('option', { name: ' Aumentar/disminuir el' }).click();
        await page.getByRole('button', { name: 'Confirmar' }).click();
        await page.locator('#pricerate').click();
    });

    test('RqId 8.1: Valor en el limite inferior del rango de entrada (-100)', async ({ page }) => {
        await page.locator('#pricerate').fill('-100');
        await page.getByRole('button', { name: 'Validar' }).click();
        await expect(page.getByText('Error, el precio no puede ser')).toBeVisible();
    });

    test('RqId 8.2: Valor válido negativo (-20)', async ({ page }) => {
        await page.locator('#pricerate').fill('-20');
        await page.getByRole('button', { name: 'Validar' }).click();
        await expect(page.getByText('registro(s) modificado(s)')).toBeVisible();
    });

    test('RqId 8.3: Valor neutro (0)', async ({ page }) => {
        await page.locator('#pricerate').fill('0');
        await page.getByRole('button', { name: 'Validar' }).click();
        await expect(page.getByText('0 registro(s) modificado(s)')).toBeVisible();
    });

    test('RqId 8.4: Valor válido positivo (100)', async ({ page }) => {
        await page.locator('#pricerate').fill('100');
        await page.getByRole('button', { name: 'Validar' }).click();
        await expect(page.getByText('registro(s) modificado(s)')).toBeVisible();
    });

    test('RqId 8.5: Valor válido positivo (300)', async ({ page }) => {
        await page.locator('#pricerate').fill('300');
        await page.getByRole('button', { name: 'Validar' }).click();
        await expect(page.getByText('registro(s) modificado(s)')).toBeVisible();
    });


    test('Caso 8: Campo vacío', async ({ page }) => {
        await page.locator('#pricerate').fill('');
        await page.getByRole('button', { name: 'Validar' }).click();
        await expect(page.getByText('0 registro(s) modificado(s)')).toBeVisible();
    });

    test('Caso 9: Valor no numérico (abc)', async ({ page }) => {
        await expect(page.locator('#pricerate').fill('abc')).rejects.toThrow('Cannot type text into input[type=number]');
    });
    
    
});
