import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost/dolibarr/htdocs/categories/categorie_list.php?type=product&type=product');
  await page.getByRole('textbox', { name: 'Login' }).click();
  await page.getByRole('textbox', { name: 'Login' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).click();
  await page.getByRole('textbox', { name: 'Password' }).fill('pgadmin123');
  await page.getByRole('button', { name: 'Login' }).click();
  await page.getByRole('link', { name: '' }).nth(2).click();
  await page.locator('#colorpickercolor').click();
  await page.locator('#colorpickercolor').fill('123456d');
  await page.getByRole('button', { name: 'Save' }).click();
  await expect(page.locator('span').filter({ hasText: 'cat1213476851287346asduiyfgsaiudfgasiuydfysauidgfiuyasdgfi34…' })).toBeVisible();
});