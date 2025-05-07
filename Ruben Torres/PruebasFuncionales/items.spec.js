import { test, expect } from '@playwright/test';

async function login(page) {
    await page.goto('https://demo.dolibarr.org/public/demo/index.php');
    await page.click('a.nomodulelines');
    await page.fill('input[name="username"]', 'demo');
    await page.fill('input[name="password"]', 'demo');
    await page.click('input[type="submit"]');

    const enlace = page.locator('a.tmenulabel[title="Services"]');
    await enlace.click();

    await page.locator('a.vsmenu[href="/product/list.php?leftmenu=service&type=1"]').click();
    await page.click('span.aaa:has-text("____BORDUREN____")');

    await page.click('#referers');
    await page.locator('a[href="/product/stats/commande.php?id=7357"]').click();
}

async function items(page, search_month, search_year, valor_busqueda) {

    await page.fill('input[name="search_month"]', search_month);
    await page.selectOption('select[name="search_year"]', search_year);
    await page.fill('input.select2-search__field', valor_busqueda);
    await page.waitForSelector('li.select2-results__option:has-text("' + valor_busqueda + '")');
    await page.click('li.select2-results__option:has-text("' + valor_busqueda + '")');

    await page.click('input[name="button_search"]');
    await page.waitForSelector('table.tagtable');
}
test('test año', async ({ page }) => {
    await login(page);

    await items(page, '', '2025', '')

});

test('test mes y año', async ({ page }) => {
    await login(page);

    await items(page, '13', '2025', '')

});

test('test año con caracteres', async ({ page }) => {
    await login(page);

    await items(page, '', 'abcd', '')

});

test('test validado', async ({ page }) => {
    await login(page);

    await items(page, '', '', 'Validated')

});

test('test Borrador', async ({ page }) => {
    await login(page);

    await items(page, '', '', 'Draft')

});

test('test Enviado', async ({ page }) => {
    await login(page);

    await items(page, '', '', 'Delivered')

});