import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
await page.goto('http://localhost/dolibarr/htdocs/categories/categorie_list.php?type=product&type=product');
  await page.getByRole('textbox', { name: 'Login' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).click();
  await page.getByRole('textbox', { name: 'Password' }).fill('pgadmin123');
  await page.getByRole('button', { name: 'Login' }).click();
  await page.getByRole('link', { name: '' }).first().click();
  await page.locator('iframe[title="Editor\\, description"]').contentFrame().locator('html').click();
  await page.locator('iframe[title="Editor\\, description"]').contentFrame().getByRole('textbox', { name: 'Editor, description' }).fill('pr\n');
  await page.locator('iframe[title="Editor\\, description"]').contentFrame().getByRole('textbox', { name: 'Editor, description' }).press('Dead');
  await page.locator('iframe[title="Editor\\, description"]').contentFrame().getByRole('textbox', { name: 'Editor, description' }).fill('prúebá tídlés |\\\n');
  await page.locator('iframe[title="Editor\\, description"]').contentFrame().getByRole('textbox', { name: 'Editor, description' }).press('Shift+Dead');
  await page.getByRole('button', { name: 'Save' }).click();
  await expect(page.locator('span').filter({ hasText: 'cat1' })).toBeVisible();
});