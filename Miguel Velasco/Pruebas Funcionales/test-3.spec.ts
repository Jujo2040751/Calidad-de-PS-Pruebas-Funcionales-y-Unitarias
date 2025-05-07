import { test, expect, Page } from '@playwright/test';
const login = async (page: Page) => {
  await page.goto('http://localhost/dolibarr/htdocs/product/stock/list.php?leftmenu=');
  await page.getByRole('textbox', { name: 'Login' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).fill('pgadmin123');
  await page.getByRole('button', { name: 'Login' }).click();
};
const getWarehouseLog = async (page: Page) => {
  await page.getByRole('link', { name: ' ALM1' }).click();
  await page.getByRole('link', { name: 'Log' }).click();
  await expect(page.getByText('Created by:')).toBeVisible();
  await page.getByText('ALM1Short name of location :').click();
  await expect(page.getByText('Open')).toBeVisible();
}
test('Get Warehouse Log', async ({ page }) => {
  await login(page);
  await getWarehouseLog(page);
});