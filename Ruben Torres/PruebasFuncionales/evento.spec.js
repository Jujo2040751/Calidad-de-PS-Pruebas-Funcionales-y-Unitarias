import { test, expect } from '@playwright/test';

test('R1 - Crear evento con datos válidos', async ({ page }) => {

  await page.goto('http://localhost/dolibarr/index.php');
  await page.fill('input[name="username"]', 'RubenTorres');
  await page.fill('input[name="password"]', 'torres123456');
  await page.click('input[type="submit"]');


  await page.click('a.tmenulabel[title="Productos | Servicios"]');
  await page.click('a.vsmenu[title="Listado"][href="/dolibarr/product/list.php?leftmenu=service&type=1"]');

  await page.click('span.aaa:has-text("0001")');
  await page.click('a.tab[title="Eventos/Agenda"]');
  await page.click('a.btnTitle[title="Crear evento"]');

  await page.fill('input[name="label"]', 'Reunión de seguimiento 2');
  await page.fill('#ap', '06/05/2025');
  await page.selectOption('#aphour', '09');
  await page.fill('#p2', '06/05/1987');
  await page.selectOption('#p2min', '30');


  await page.click('#select2-assignedtouser-container');
  await page.fill('input.select2-search__field', 'SuperAdmin');
  await page.click('.select2-results__option >> text=SuperAdmin');


  await page.click('input[type="submit"][name="add"]');

});