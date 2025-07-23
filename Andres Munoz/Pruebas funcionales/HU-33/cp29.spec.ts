import { test, expect } from '@playwright/test';

test('CP29 - Crear nuevo almacén con teléfono mayor a 15 caracteres', async ({ page }) => {

  const datos = {
    "referencia": "015AlmacenExpansivo",
    "nombreDelAlmacen": "Almacén Oeste",
    "direccion": "Calle Ríos 210",
    "codigoPostal": "28007",
    "ciudad": "Madrid",
    "telefono": "+34 9123456789101112",
    "fax": "+34 9123456789101113",
    "descripcion": "Almacén ubicado en una zona estratégica, especializado en logística y distribución de productos a gran escala. Cuenta con un sistema de gestión integrado y personal altamente capacitado."
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