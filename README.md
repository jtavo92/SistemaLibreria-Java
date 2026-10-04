# SistemaLibreria-Java

## 📚 Introducción

**SistemaLibreria-Java** es un sistema desarrollado en Java para gestionar las operaciones básicas de una librería. El proyecto busca aplicar conceptos de Programación Orientada a Objetos (POO), permitiendo administrar productos, clientes y ventas de manera sencilla mediante un menú interactivo.

## 🎯 Objetivo

Desarrollar una aplicación de gestión para una librería, aplicando buenas prácticas de programación, organización del código y trabajo colaborativo utilizando Git y GitHub.

## ⚙️ Funcionalidades

* Gestión de productos.
* Registro y consulta de productos.
* Registro de clientes.
* Gestión de pedidos y ventas.
* Validación de datos.
* Menú principal de navegación.

## 🛠️ Tecnologías

* Java
* NetBeans
* H2 Database en memoria
* Git
* GitHub

## ▶️ Ejecutar desde PowerShell

Desde la carpeta raíz del proyecto, compila y ejecuta con:

```powershell
javac -cp lib\h2-2.3.232.jar -d build\classes -sourcepath src src\menu\Main.java
java -cp "build\classes;lib\h2-2.3.232.jar" menu.Main
```

H2 funciona en memoria y no requiere instalar ni iniciar un servidor. Los datos
de productos se reinician al cerrar la aplicación.

## 👥 Integrantes

* LEONARDO ADRIANO CRUZ MORAN
* LUIS ALBERTO LEVANO LIZANA
* KEVIN YEANDER OSCO OSORES
* MARISOL TIRACCAYA ARROYO
* ERICK EDUARDO VERGARA HONORES
* JOSE GUSTAVO ZAPATA DE LA CRUZ

## 📌 Estado del proyecto

Proyecto en desarrollo.
