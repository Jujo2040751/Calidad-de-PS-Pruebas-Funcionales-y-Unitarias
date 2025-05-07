import { test, expect } from '@playwright/test';

test('CP3 - Crear nuevo almacén sin referencia', async ({ page }) => {

  const datos = {
    "referencia": "",
    "nombreDelAlmacen": "Zona Este de productos electrónicos",
    "direccion": "Calle de la Tecnología 101",
    "codigoPostal": "28001",
    "ciudad": "Madrid",
    "telefono": "+34 911223344",
    "fax": "+34 911223345",
    "descripcion": "Área especializada en componentes eléctricos"
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
  await expect(page.locator('input[name="libelle"]')).toBeVisible();

});