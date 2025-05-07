import { test, expect, Page } from '@playwright/test';
const login = async (page: Page) => {
  await page.goto('http://localhost/dolibarr/htdocs/product/stock/list.php?leftmenu=');
  await page.getByRole('textbox', { name: 'Login' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).fill('pgadmin123');
  await page.getByRole('button', { name: 'Login' }).click();
};
const getWarehouseData = async (page: Page) => {
  await page.getByRole('link', { name: ' ALM1' }).click();
await expect(page.getByText('ALM1Short name of location :')).toBeVisible();
await expect(page.getByRole('cell', { name: 'Description' })).toBeVisible();
await expect(page.getByRole('cell', { name: 'Number of unique products' })).toBeVisible();
await expect(page.getByRole('cell', { name: 'Total number of products' })).toBeVisible();
await expect(page.locator('#dragDropAreaTabBar').getByRole('cell', { name: 'Input stock value' })).toBeVisible();
await expect(page.getByRole('cell', { name: 'Latest movement' })).toBeVisible();
await expect(page.getByRole('cell', { name: 'Tags/categories' })).toBeVisible();
}
test('Get Warehouse Data', async ({ page }) => {
  await login(page);
  await getWarehouseData(page);
});