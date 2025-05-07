import { test, expect } from '@playwright/test';

test('CP15 - Crear nuevo almacén con descripción mayor a 255 caracteres', async ({ page }) => {

  const datos = {
    "referencia": "008AlmacenDescripcionLarga",
    "nombreDelAlmacen": "Almacén Este",
    "direccion": "Av. del Este 120",
    "codigoPostal": "28012",
    "ciudad": "Madrid",
    "telefono": "+34 914223344",
    "fax": "+34 914223345",
    "descripcion": "Este almacén tiene una descripción demasiado extensa. Fue construido en 1995, renovado en 2020, y cuenta con más de 5000 m² de espacio, zonas de carga automatizadas, paneles solares, sistema de gestión por RFID, áreas de picking, oficinas y comedor interno para el personal."
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
  await expect(page.locator('body')).not.toContainText(datos.referencia);

});