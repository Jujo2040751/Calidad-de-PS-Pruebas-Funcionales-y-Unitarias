import { test, expect } from '@playwright/test';

// Funcionales - PARTICIONES DE EQUIVALENCIA: listado de productos (DLB-8)

test.describe('Listado de Productos - Particiones de Equivalencia', () => {

    test.beforeEach(async ({ page }) => {
        await page.goto('http://localhost/dolibarr/');
        await page.getByRole('textbox', { name: 'Login' }).fill('admin1');
        await page.getByRole('textbox', { name: 'Contraseña' }).fill('pass1234');
        await page.getByRole('button', { name: 'Conexión' }).click();
        await page.getByRole('link', { name: 'Productos | Servicios' }).click();
        await page.getByRole('link', { name: 'Listado' }).first().click();
    });

    test('RqId 7.1: Visualización de columnas por defecto', async ({ page }) => {
        await expect(page.getByRole('link', { name: 'Producto ref.' })).toBeVisible();
        await expect(page.getByRole('link', { name: 'Etiqueta' })).toBeVisible();
        await expect(page.getByRole('cell', { name: 'Precio de venta' })).toBeVisible();
        await expect(page.getByRole('cell', { name: 'Mejor precio de compra' })).toBeVisible();
        await expect(page.getByRole('link', { name: 'Stock deseado' })).toBeVisible();
        await expect(page.getByRole('link', { name: 'Inventario FISICO' })).toBeVisible();
    });

    test('RqId 7.2: Búsqueda con "Ref. producto" existente', async ({ page }) => {
        await page.locator('input[name="search_ref"]').fill('CremaPeinar');
        await page.getByRole('button', { name: '' }).click();
        await expect(page.getByRole('link', { name: ' CremaPeinar', exact: true })).toBeVisible();
    });

    test('RqId 7.3: Búsqueda con "Ref. producto" inexistente', async ({ page }) => {
        await page.locator('input[name="search_ref"]').fill('productoInexistente123');
        await page.getByRole('button', { name: '' }).click();
        await expect(page.getByText("No se encontraron registros")).toBeVisible();
    });

    test('RqId 7.4: Cambio a vista Kanban', async ({ page }) => {
        await page.getByRole('link', { name: '' }).click();
        await expect(page.locator('.trkanban')).toBeVisible();
    });

    test('RqId 7.5: Cambiar a paginación de 50 elementos', async ({ page }) => {
        await page.getByRole('combobox', { name: '20' }).click();
        await page.getByRole('option', { name: '50', exact: true }).click();
        const filas = page.locator('table tbody tr');
        const count = await filas.count();
        expect(count).toBeGreaterThan(1);
    });

    test('RqId 7.7: Visualización del botón "Añadir producto"', async ({ page }) => {
        await expect(page.getByRole('link', { name: 'Nuevo producto' })).toBeVisible();
    });

    test('RqId 7.8: Conteo de productos visibles', async ({ page }) => {
        const resumen = page.locator('div.titre.inline-block');
        await expect(resumen).toHaveText(/Productos\s*\(\d+\)/);
      });      

});