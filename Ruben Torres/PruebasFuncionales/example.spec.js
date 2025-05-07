import { test, expect } from '@playwright/test';

async function login(page) {
  await page.goto('http://localhost/dolibarr/index.php');
  await page.fill('input[name="username"]', 'RubenTorres');
  await page.fill('input[name="password"]', 'torres123456');
  await page.click('input[type="submit"]');
}

async function crearServicio(page, ref: string, label: string, duration_value: string, price: string, price_min: string, accountancy_code_sell: string, accountancy_code_sell_export: string, accountancy_code_buy: string, accountancy_code_buy_export: string ) {
  const enlace = page.locator('a.tmenulabel[title="Productos | Servicios"]');
  await enlace.click();

  const enlace1 = page.locator('a.vsmenu[title="Nuevo servicio"]');
  await enlace1.click();

  await page.fill('input[name="ref"]', ref);
  await page.fill('input[name="label"]', label);
  const frameLocator = page.frameLocator('iframe.cke_wysiwyg_frame');
  const editorBody = frameLocator.locator('body');
  await editorBody.waitFor({ state: 'visible' });
  await editorBody.fill('Instalación individual de tubería de PVC, adecuada para sistemas de conducción de agua u otros fluidos. Trabajo realizado conforme a normativas de seguridad.');
  await page.fill('input[name="duration_value"]', duration_value);
  await page.selectOption('#duration_unit', { value: 'h' });
  await page.fill('#note_private', 'Este es un comentario privado.');
  await page.fill('input[name="price"]', price);
  await page.fill('input[name="price_min"]', price_min);
  await page.selectOption('#select_price_base_type', { value: 'TTC' });
  await page.fill('input[name="accountancy_code_sell"]', accountancy_code_sell);
  await page.fill('input[name="accountancy_code_sell_export"]', accountancy_code_sell_export);
  await page.fill('input[name="accountancy_code_buy"]', accountancy_code_buy);
  await page.fill('input[name="accountancy_code_buy_export"]', accountancy_code_buy_export);
  
  await page.click('input[name="add"]');
 

}


  async function irAlListado(page) {
    const enlace2 = page.locator('a.vsmenu[title="Listado"][href="/dolibarr/product/list.php?leftmenu=service&type=1"]');
    await enlace2.click();
  }
test('tiempo1', async ({ page }) => { 
  await login(page);
  await crearServicio(page, '0001', 'Instalacion de Tuberia PVC UND', '2', '10000', '9500', '700', '710', '600', '620');
  await irAlListado(page);
});

test('tiempo2', async ({ page }) => {
  await login(page);
  await crearServicio(page, '0002', 'Instalacion de Tuberia PVC UND', '-1', '9999999999999999999999999999999999999999999999', '999999999999999999999999999999', '700', '710', '600', '620');
  await irAlListado(page);
});

test('tiempo3', async ({ page }) => {
  await login(page);
  await crearServicio(page, '0003', 'Instalacion de Tuberia PVC UND', '0', '0', '-2', '700', '710', '600', '620');
  await irAlListado(page);
});

test('tiempo4', async ({ page }) => {
  await login(page);
  await crearServicio(page, '0004', 'Instalacion de Tuberia PVC UND', 'dos', '-100', '-98', '700', '710', '600','620');
  await irAlListado(page);
});