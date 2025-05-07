import { test, expect } from '@playwright/test';

async function login(page) {
    await page.goto('http://localhost/dolibarr/index.php');
    await page.fill('input[name="username"]', 'RubenTorres');
    await page.fill('input[name="password"]', 'torres123456');
    await page.click('input[type="submit"]');

    const enlace = page.locator('a.tmenulabel[title="Productos | Servicios"]');
    await enlace.click();

    
}

async function listado(page, search_ref) {
    const enlace2 = page.locator('a.vsmenu[title="Listado"][href="/dolibarr/product/list.php?leftmenu=service&type=1"]');
    await enlace2.click();

    await page.fill('input[name="search_ref"]', search_ref);
    await page.locator('input[name="search_ref"]').press('Enter');
}

test('CP1 - Referencia válida existente', async ({ page }) => {
    await login(page);  
    await listado(page, '0001'); 
 });
 test('CP2 - Referencia inválida inexistente', async ({ page }) => {
    await login(page);
    await listado(page, '9999');  
});

test('CP3 - Referencia no numérica', async ({ page }) => {
    await login(page);
    await listado(page, 'ABC'); 
});

test('CP4 - Campo vacío', async ({ page }) => {
    await login(page);
    await listado(page, ''); 
});
test('CP5 - Referencia parcial válida', async ({ page }) => {
    await login(page);
    await listado(page, '00'); 
});

test('CP6 - Referencia con símbolos', async ({ page }) => {
    await login(page);
    await listado(page, '0001!');
});
