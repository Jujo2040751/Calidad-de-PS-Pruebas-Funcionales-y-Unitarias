import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost/dolibarr/htdocs/categories/categorie_list.php?type=product&type=product');
  await page.getByRole('textbox', { name: 'Login' }).click();
  await page.getByRole('textbox', { name: 'Login' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).click();
  await page.getByRole('textbox', { name: 'Password' }).fill('pgadmin123');
  await page.getByRole('button', { name: 'Login' }).click();
  await page.getByRole('cell', { name: '' }).nth(3).click();
  await page.locator('#position').click();
  await page.locator('#position').fill('19asd9');
  await page.getByRole('button', { name: 'Save' }).click();
  await expect(page.getByText('ImpossibleUpdate : This')).toBeVisible();
});