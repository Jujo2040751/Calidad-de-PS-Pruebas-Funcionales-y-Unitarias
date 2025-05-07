import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost/dolibarr/htdocs/categories/categorie_list.php?type=product&type=product');
  await page.getByRole('textbox', { name: 'Login' }).click();
  await page.getByRole('textbox', { name: 'Login' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).click();
  await page.getByRole('textbox', { name: 'Password' }).fill('pgadmin123');
  await page.getByRole('button', { name: 'Login' }).click();
  await page.getByRole('link', { name: '' }).first().click();
  await page.locator('#label').click();
  await page.locator('#label').fill('cat2');
  await page.getByRole('button', { name: 'Save' }).click();
  await expect(page.getByText('ImpossibleUpdateCat : This')).toBeVisible();
});