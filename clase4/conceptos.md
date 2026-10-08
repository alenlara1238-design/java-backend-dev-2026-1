# Manejo centralizado de excepciones en Spring Boot

Cuando desarrollamos una API REST, es normal que ocurran situaciones que impidan completar una operación.

Por ejemplo, nuestro servicio intenta buscar un producto, pero ese producto no existe.

Podríamos pensar inicialmente en utilizar `try-catch` directamente dentro de cada controlador. Sin embargo, esto rápidamente puede generar código repetido y mezclar responsabilidades.

Spring Boot nos permite solucionar este problema mediante un **manejador centralizado de excepciones**.

---

## 1. ¿Qué significa manejar las excepciones de forma centralizada?

La idea principal es sencilla:

> **El servicio detecta el problema y lanza la excepción. El manejador centralizado decide cómo convertir esa excepción en una respuesta HTTP.**

Por ejemplo:

```java
public Product buscarProducto(Long id) {

    Product producto = repository.findById(id).orElse(null);

    if (producto == null) {
        throw new ProductoNoEncontradoException(
            "Producto no encontrado"
        );
    }

    return producto;
}
```

El servicio no necesita construir una respuesta HTTP.

Simplemente informa:

```java
throw new ProductoNoEncontradoException(
    "Producto no encontrado"
);
```

---

# 2. ¿Por qué no hacemos un try-catch en cada controlador?

Podríamos hacer algo como esto:

```java
@GetMapping("/{id}")
public ResponseEntity<?> buscar(@PathVariable Long id) {

    try {
        Product producto = service.buscarProducto(id);
        return ResponseEntity.ok(producto);

    } catch (ProductoNoEncontradoException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
```

El problema aparece cuando tenemos muchos controladores.

Podríamos terminar repitiendo continuamente:

```java
try {
    ...
} catch (...) {
    ...
}
```

Además, el controlador comenzaría a preocuparse por algo que no debería ser su responsabilidad principal:

> ¿Cómo debo responder cuando ocurre cada tipo de error?

El controlador debería concentrarse principalmente en recibir la petición y delegar la operación.

---

# 3. El manejador centralizado

Spring Boot permite crear un componente que se encargue de manejar las excepciones de nuestra aplicación.

Para esto podemos utilizar:

```java
@RestControllerAdvice
```

Por ejemplo:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<String> manejarProductoNoEncontrado(
            ProductoNoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
```

Aquí estamos diciendo:

> "Cada vez que ocurra una `ProductoNoEncontradoException`, utiliza este método para construir la respuesta."

---

# 4. ¿Qué sucede cuando lanzamos la excepción?

Supongamos que nuestro servicio tiene:

```java
public Product buscarProducto(Long id) {

    Product producto = repository.findById(id).orElse(null);

    if (producto == null) {
        throw new ProductoNoEncontradoException(
            "Producto 15 no encontrado"
        );
    }

    return producto;
}
```

La excepción se lanza:

```text
Service
   │
   │ throw new ProductoNoEncontradoException(...)
   ↓
Excepción
   │
   ↓
GlobalExceptionHandler
   │
   ↓
HTTP 404
```

El controlador no necesita capturarla mediante `try-catch`.

Por ejemplo:

```java
@GetMapping("/{id}")
public Product buscar(@PathVariable Long id) {

    return service.buscarProducto(id);
}
```

Si el producto existe:

```text
Controller
    ↓
Service
    ↓
Product
    ↓
HTTP 200
```

Si el producto no existe:

```text
Controller
    ↓
Service
    ↓
throw new ProductoNoEncontradoException(...)
    ↓
GlobalExceptionHandler
    ↓
HTTP 404
```

---

# 5. ¿La respuesta siempre será igual?

Si configuramos un manejador para un determinado tipo de excepción, **todas las excepciones de ese tipo tendrán el mismo tratamiento**.

Por ejemplo:

```java
@ExceptionHandler(ProductoNoEncontradoException.class)
public ResponseEntity<String> manejarProductoNoEncontrado(
        ProductoNoEncontradoException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}
```

Si ocurre:

```java
throw new ProductoNoEncontradoException(
    "Producto 15 no encontrado"
);
```

obtendremos:

```text
HTTP 404
Producto 15 no encontrado
```

Si ocurre:

```java
throw new ProductoNoEncontradoException(
    "Producto 80 no encontrado"
);
```

obtendremos:

```text
HTTP 404
Producto 80 no encontrado
```

La información puede cambiar, pero **el tratamiento es el mismo**:

```text
ProductoNoEncontradoException
           ↓
        HTTP 404
```

---

# 6. Podemos tener diferentes tipos de excepciones

Una aplicación puede tener diferentes problemas y, por lo tanto, diferentes tratamientos.

Por ejemplo:

```java
@ExceptionHandler(ProductoNoEncontradoException.class)
→ HTTP 404 NOT_FOUND
```

Una excepción relacionada con datos inválidos:

```java
@ExceptionHandler(IllegalArgumentException.class)
→ HTTP 400 BAD_REQUEST
```

Y podemos tener un manejador general para errores inesperados:

```java
@ExceptionHandler(Exception.class)
→ HTTP 500 INTERNAL_SERVER_ERROR
```

Podemos imaginarlo así:

```text
                    EXCEPCIÓN
                       │
          ┌────────────┼────────────┐
          │            │            │
          ▼            ▼            ▼
 ProductoNo       Illegal       Exception
 Encontrado       Argument
          │            │            │
          ▼            ▼            ▼
        404          400          500
```

---

# 7. ¿Entonces el objetivo es evitar código repetido?

**Evitar código repetido es una ventaja, pero no es la idea principal.**

El objetivo principal es:

> **Centralizar y estandarizar la forma en que nuestra aplicación transforma las excepciones en respuestas HTTP.**

Gracias a esta centralización conseguimos además:

- Evitar `try-catch` repetidos en los controladores.
- Separar responsabilidades.
- Mantener los servicios más limpios.
- Mantener los controladores enfocados en atender peticiones.
- Definir códigos HTTP de manera consistente.
- Mantener una estructura uniforme para las respuestas de error.

---

# 8. La responsabilidad de cada componente

Podemos verlo de esta manera:

### Service

Detecta que algo salió mal:

```java
if (producto == null) {
    throw new ProductoNoEncontradoException(
        "Producto no encontrado"
    );
}
```

Su responsabilidad es:

> **Detectar y comunicar el problema.**

---

### Controller

Recibe la petición y delega la operación:

```java
@GetMapping("/{id}")
public Product buscar(@PathVariable Long id) {

    return service.buscarProducto(id);
}
```

Su responsabilidad es:

> **Atender la petición y delegar el trabajo.**

---

### GlobalExceptionHandler

Decide cómo responder al cliente:

```java
@ExceptionHandler(ProductoNoEncontradoException.class)
public ResponseEntity<String> manejar(
        ProductoNoEncontradoException ex) {

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
}
```

Su responsabilidad es:

> **Transformar la excepción en una respuesta HTTP.**

---

# ejercicio de práctica: API de productos en memoria

## 1. Enunciado

Construir una pequeña API REST para gestionar productos.

El sistema permitirá:

- Consultar todos los productos.
- Buscar un producto por su `id`.
- Registrar un nuevo producto.

La arquitectura estará separada en cuatro responsabilidades:

```text
Model → representa los datos
Repository → administra los productos en memoria
Service → contiene la lógica de negocio
Controller → expone los endpoints HTTP
```

---

# 2. Función del Service

El `ProductService` será responsable de coordinar las operaciones relacionadas con los productos.

Por ejemplo, cuando un usuario solicite un producto por su `id`, el servicio consultará el repositorio.

Si el producto existe, lo devolverá.

Si no existe, el servicio deberá detectar esta situación y lanzar una excepción:

```java
throw new ProductoNoEncontradoException(...)
```

De esta manera, el servicio **no se preocupará por construir la respuesta HTTP**.

Posteriormente, nuestro `GlobalExceptionHandler` será responsable de transformar esa excepción en una respuesta HTTP adecuada.

El flujo será:

```text
Cliente
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Lista en memoria
```

Y cuando ocurra un error:

```text
Repository
    ↓
Service
    ↓
throw new ProductoNoEncontradoException
    ↓
GlobalExceptionHandler
    ↓
HTTP 404
```

# Validaciones y manejo de errores en una API Spring Boot

Hasta ahora hemos aprendido que una excepción puede representar una situación que impide que una operación continúe normalmente.

Por ejemplo, nuestro servicio puede intentar buscar un producto que no existe:

```java
throw new ProductoNoEncontradoException(
    "Producto no encontrado"
);
```

En ese caso, el `GlobalExceptionHandler` se encarga de transformar la excepción en una respuesta HTTP.

Ahora aparece una situación diferente:

> ¿Qué ocurre cuando el problema está en los datos que el cliente está enviando?

---

# 1. Los datos recibidos también pueden ser incorrectos

Supongamos que nuestra API permite registrar productos.

El cliente podría enviar:

```json
{
    "nombre": "",
    "precio": -5000
}
```

Aquí todavía no tenemos un problema con la base de datos ni con nuestro repositorio.

El problema está en los **datos recibidos**.

Podríamos establecer algunas reglas:

* El nombre es obligatorio.
* El nombre no puede estar vacío.
* El precio debe ser mayor que cero.

Por lo tanto, antes de enviar los datos al servicio debemos preguntarnos:

> **¿Los datos recibidos cumplen las reglas que hemos definido?**

---

# 2. La validación como filtro

Podemos imaginar la validación como un filtro que debe atravesar la información antes de llegar al servicio.

```text
Cliente envía datos
        ↓
Se validan los datos
        ↓
   ¿Son válidos?
      ↓       ↓
     Sí       No
     ↓         ↓
 Servicio     Error
                ↓
       Manejo centralizado
```

La idea fundamental es:

> **Si los datos no cumplen las reglas, la operación no debería continuar hacia el servicio.**

---

# 3. ¿Por qué validar antes de llegar al Service?

Supongamos que recibimos:

```json
{
    "nombre": "",
    "precio": -5000
}
```

No tendría sentido permitir que estos datos lleguen hasta nuestra lógica de negocio para después descubrir que son incorrectos.

La validación puede detectar el problema inmediatamente.

```text
Request
   ↓
Validación
   ↓
❌ Datos inválidos
   ↓
Error
```

En cambio, si los datos son correctos:

```text
Request
   ↓
Validación
   ↓
✓ Datos válidos
   ↓
Service
```

Por eso podemos considerar la validación como una primera barrera de protección de nuestra aplicación.

---

# 4. Validación y lógica de negocio son cosas diferentes

Es importante distinguir estos dos conceptos.

Una **validación** comprueba que los datos recibidos cumplen determinadas condiciones.

Por ejemplo:

```text
nombre → obligatorio
precio → mayor que 0
```

Mientras que la **lógica de negocio** puede responder preguntas como:

```text
¿El producto existe?
¿Se puede modificar?
¿Hay unidades disponibles?
¿El usuario tiene permiso?
```

Por ejemplo:

```java
if (producto == null) {
    throw new ProductoNoEncontradoException(...);
}
```

Esto no es una validación de los datos recibidos.

Es una regla relacionada con la operación que estamos intentando realizar.

---

# 5. Dos tipos de problemas diferentes

Podemos comenzar a distinguir dos situaciones:

### Datos inválidos

El cliente envía información que no cumple las reglas.

```json
{
    "nombre": "",
    "precio": -5000
}
```

La validación detecta el problema.

```text
Datos inválidos
      ↓
Error de validación
```

---

### Recurso inexistente

El cliente puede haber enviado datos perfectamente válidos, pero está intentando acceder a algo que no existe.

Por ejemplo:

```text
GET /api/products/99
```

El producto `99` simplemente no existe.

Nuestro servicio puede lanzar:

```java
throw new ProductoNoEncontradoException(
    "Producto con id 99 no encontrado"
);
```

Aquí interviene nuestro manejo centralizado de excepciones.

---

# 6. El flujo completo empieza a crecer

Ahora podemos visualizar nuestra API de una manera más completa:

```text
                 Cliente
                    ↓
              HTTP Request
                    ↓
               Validación
                    ↓
              ¿Datos válidos?
                /       \
              Sí         No
              ↓           ↓
           Service       Error
              ↓           ↓
         Lógica de     Manejo
          negocio    centralizado
              ↓
          Repository
```

Esto nos permite entender que **no todos los errores son iguales**.

Un error puede aparecer porque:

```text
Los datos recibidos son inválidos
```

o porque:

```text
La operación no puede realizarse
```

y ambos pueden terminar siendo transformados en respuestas HTTP mediante un manejo centralizado.

---

# 7. ¿Qué queremos conseguir?

Queremos que el cliente reciba una respuesta clara cuando envía información incorrecta.

Por ejemplo, si envía:

```json
{
    "nombre": "",
    "precio": -5000
}
```

no queremos simplemente recibir un error genérico.

Queremos que nuestra API pueda comunicar algo como:

```json
{
    "mensaje": "Los datos enviados no son válidos"
}
```

Incluso posteriormente podremos devolver información más específica:

```json
{
    "nombre": "El nombre es obligatorio",
    "precio": "El precio debe ser mayor que cero"
}
```

De esta manera, el cliente puede saber **qué debe corregir**.

---
La siguiente etapa será aprender cómo Spring Boot puede realizar estas validaciones automáticamente utilizando **Jakarta Bean Validation**, mediante anotaciones como:

```java
@NotBlank
```

```java
@Positive
```

y

```java
@Size
```

De esta manera podremos declarar las reglas directamente sobre nuestro `Product` y dejar que Spring detecte automáticamente cuando los datos enviados por el cliente no las cumplen.
