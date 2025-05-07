import { test, expect, Page } from '@playwright/test';

const login = async (page: Page) => {
  await page.goto('http://localhost/dolibarr/htdocs/categories/categorie_list.php?type=product&type=product');
  await page.getByRole('textbox', { name: 'Login' }).fill('admin');
  await page.getByRole('textbox', { name: 'Password' }).fill('pgadmin123');
  await page.getByRole('button', { name: 'Login' }).click();
};

const goToCategories = async (page: Page) => {
  await page.getByRole('link', { name: '' }).click();
  await page.getByRole('link', { name: 'Tags/categories' }).first().dblclick();
};

const editCategory = async (page: Page, nameAfter: string) => {
  await page.getByRole('link', { name: '' }).first().click();
  await page.locator('#label').click();
  await page.locator('#label').fill('cat4');
  await page.getByRole('button', { name: 'Save' }).click();
  await expect(page.getByRole('link', { name: nameAfter })).toBeVisible();
};
test('Edit category name', async ({ page }) => {
  const categoryNameAfter = 'cat4';

  await login(page);
  await goToCategories(page);
  await editCategory(page, categoryNameAfter);
});