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

const createCategory = async (page: Page, name: string, description: string) => {
  await page.getByRole('link', { name: '' }).dblclick(); // Add new
  await page.getByRole('textbox', { name: 'My tag' }).fill(name);
  const frame = page.frameLocator('iframe[title="Editor\\, description"]');
  await frame.getByRole('textbox', { name: 'Editor, description' }).fill(description);
  await page.locator('#colorpickercolor').dblclick();
  await page.getByRole('button', { name: 'Create this tag/category' }).dblclick();
};

const assertCategoryVisible = async (page: Page, name: string) => {
  await expect(page.getByRole('link', { name })).toBeVisible();
};

const assertCategoryDescription = async (page: Page, name: string, expectedDescription: string) => {
  // Click on the category name to view its details
  await page.getByRole('link', { name }).click();

  // Assert the description is visible in a cell
  await expect(
    page.getByRole('cell', {
      name: expectedDescription,
    })
  ).toBeVisible();
};

// Final test using the helpers
test('Create and validate "Deportes" category', async ({ page }) => {
  const categoryName = 'Deportess';
  const categoryDescription = 'Esta categoría corresponde a los items o servicios relacionados con deportes';

  await login(page);
  await goToCategories(page);
  await createCategory(page, categoryName, categoryDescription);
  await assertCategoryVisible(page, categoryName);
  await assertCategoryDescription(page, categoryName, categoryDescription);
});
