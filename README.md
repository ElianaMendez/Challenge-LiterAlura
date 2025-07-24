# LiterAlura - Catálogo de Libros

![Estado del Proyecto](https://img.shields.io/badge/status-finished-green)
![Java](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.3-green?logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql&logoColor=white)

LiterAlura es una aplicación de consola en Java que funciona como un catálogo de libros. Permite a los usuarios interactuar con la API de [Gutendex](https://gutendex.com/) para buscar libros y autores, y persistir la información relevante en una base de datos PostgreSQL local.

Este proyecto fue desarrollado como parte del Challenge de Programación de **Alura Latam** en colaboración con **Oracle Next Education (ONE)**. El objetivo específico del proyecto fue trabajar con lambdas, streams, Spring Framework, persistencia de datos y consultas con Spring Data JPA.

## ✨ Características Principales

* **Búsqueda de Libros por Título:** Consume la API de Gutendex para encontrar libros por su título.
* **Persistencia de Datos:** Guarda la información de los libros y autores buscados en una base de datos PostgreSQL para consultas futuras.
* **Listado de Libros Registrados:** Muestra todos los libros que han sido guardados en la base de datos local.
* **Listado de Autores Registrados:** Muestra todos los autores almacenados localmente.
* **Consulta de Autores Vivos:** Permite listar autores que estaban vivos en un determinado año.
* **Búsqueda de Libros por Idioma:** Filtra y muestra los libros registrados en la base de datos según el idioma (Español, Inglés, Francés, Portugués).

## 🛠️ Tecnologías Utilizadas

* **Backend:**
    * Java 21
    * Spring Boot 3.5.3
    * Spring Data JPA
* **Base de Datos:**
    * PostgreSQL
* **API:**
    * Consumo de la API REST de [Gutendex](https://gutendex.com/).
* **Manejo de Dependencias:**
    * Maven

## ⚙️ Configuración y Ejecución

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local.

### **Prerrequisitos**

* Tener instalado **Java Development Kit (JDK) 17** o superior.
* Tener instalado **Apache Maven**.
* Tener instalado y en ejecución un servidor de base de datos **PostgreSQL**.

### **Pasos**

1.  **Clona el repositorio:**
    ```bash
    git clone [https://github.com/ElianaMendez/Challenge-LiterAlura.git](https://github.com/ElianaMendez/Challenge-LiterAlura.git)
    cd Challenge-LiterAlura
    ```

2.  **Configura la Base de Datos:**
    * Abre PostgreSQL y crea una nueva base de datos. Puedes llamarla `literalura_db`.
    * En la raíz del proyecto, encontrarás el archivo `application.properties` dentro de `src/main/resources`.
    * Modifica las siguientes líneas en `application.properties` con tus credenciales de PostgreSQL:

    ```properties
    # PostgreSQL server
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
    spring.datasource.username=TU_USUARIO_POSTGRES
    spring.datasource.password=TU_CONTRASEÑA_POSTGRES
    ```

3.  **Construye y Ejecuta la Aplicación:**
    * Abre una terminal en la raíz del proyecto.
    * Usa Maven para construir y ejecutar el proyecto con un solo comando:
    ```bash
    mvn spring-boot:run
    ```
    * La aplicación se iniciará y verás el menú interactivo en la consola.

## 💻 Uso de la Aplicación

Una vez que la aplicación esté en ejecución, verás un menú como este:

```text
*******************************************
Elija la opción a través de su número:
1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma
0 - Salir
*******************************************

## 💻 Demo de la Aplicación

![Demo](demo.gif)
