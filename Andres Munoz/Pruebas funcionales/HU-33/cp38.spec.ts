import { test, expect } from '@playwright/test';

test('CP38 - Crear nuevo almacén con algunos datos no obligatorios vacios', async ({ page }) => {

  const datos = {
    "referencia": "018AlmacenSinDatos",
    "nombreDelAlmacen": "Almacén Auxiliar",
    "direccion": "Camino Viejo 33",
    "codigoPostal": "",
    "ciudad": "Madrid",
    "telefono": "",
    "fax": "",
    "descripcion": "Este almacén funciona como punto de apoyo para las operaciones logísticas de otras sedes. Aunque es pequeño, su ubicación lo hace estratégico para entregas locales rápidas."
  }
  
  await page.goto('https://demo.dolibarr.org/product/index.php?mainmenu=products&leftmenu=');

  // Inicio de sesión
  await page.getByRole('textbox', { name: 'Login' }).fill('demo');
  await page.getByRole('textbox', { name: 'Password' }).fill('demo');
  await page.getByRole('button', { name: 'Login' }).click();

  // Ir a módulo de almacenes, nuevo almacén
  await page.getByRole('link', { name: 'New warehouse' }).click();

  // Rellenar formulario

  //Referencia
  await page.locator('input[name="libelle"]').fill(datos.referencia);

  // Nombre de la ubicacion
  await page.locator('input[name="lieu"]').fill(datos.nombreDelAlmacen);

  // Descripción
  await page.locator('iframe[title="Editor\\, desc"]').contentFrame().locator('body').fill(datos.descripcion);

  // Dirección
  await page.locator('textarea[name="address"]').fill(datos.direccion);

  // Código postal
  await page.locator('#zipcode').fill(datos.codigoPostal);

  // Ciudad
  await page.locator('#town').fill(datos.ciudad);
  await page.getByText('Madrid (Spain)', { exact: true }).click();

  // Teléfono y fax
  await page.locator('input[name="phone"]').fill(datos.telefono);
  await page.locator('input[name="fax"]').fill(datos.fax);

  // Estado por defecto activo

  // Valores omitidos
  // Relación con otro almacén
  // Proyecto
  // País
  // Etiquetas
  // categorías

  // Guardar
  await page.getByRole('button', { name: 'Create' }).click();

  // Verificación: guardó con éxito del nuevo almacén
  await expect(page.locator('body')).toContainText(datos.referencia);

});