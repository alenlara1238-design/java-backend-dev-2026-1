# ¿Qué es un JSON y cómo se usa?

## 1. ¿Qué significa JSON?

**JSON** significa **JavaScript Object Notation** (Notación de Objetos
de JavaScript). Aunque lleve "JavaScript" en el nombre, hoy en día es un
estándar universal.

**En pocas palabras:** JSON es el "idioma" oficial que usan las
aplicaciones en internet para comunicarse entre ellas.

Cuando un backend desarrollado en **Spring Boot** se comunica con una
aplicación móvil o un frontend desarrollado en **React**, los datos
normalmente se envían en formato JSON porque es ligero y fácil de leer
tanto para humanos como para computadoras.

------------------------------------------------------------------------

## 2. La regla de oro: Estructura de Clave / Valor

Un JSON siempre se escribe entre llaves `{ }` y almacena la información
en parejas:

``` json
"clave": valor
```

### Clave

Siempre va entre comillas dobles `""` y representa el nombre de la
propiedad.

### Valor

Es el dato real que queremos guardar. Puede ser texto, números,
booleanos, etc.

Ejemplo básico:

``` json
{
  "nombre": "Carlos Gómez",
  "edad": 21,
  "esActivo": true
}
```

------------------------------------------------------------------------

## 3. Tipos de datos permitidos en JSON

### Texto (String)

``` json
"ciudad": "Bogotá"
```

### Números (Number)

``` json
"precio": 19.99
```

### Booleanos

``` json
"graduado": false
```

### Nulos

``` json
"telefono": null
```

### Arreglos (Arrays)

``` json
"generos": ["Pop", "Rock"]
```

### Objetos

Un JSON dentro de otro JSON:

``` json
{
}
```

------------------------------------------------------------------------

# 4. Ejemplos progresivos

## Ejemplo 1: JSON de una canción

``` json
{
  "titulo": "Entre dos tierras"
}
```

Spring Boot recibe la clave `"titulo"` y la asigna al atributo:

``` java
private String titulo;
```

usando:

``` java
@RequestBody
```

------------------------------------------------------------------------

## Ejemplo 2: Objeto con múltiples datos

``` json
{
  "titulo": "Mi historia entre tus dedos",
  "artista": "Gianluca Grignani",
  "duracionSegundos": 202,
  "esSencillo": true
}
```

------------------------------------------------------------------------

## Ejemplo 3: Arrays y objetos anidados

``` json
{
  "titulo": "La Bachata",
  "artista": "Manuel Turizo",
  "generos": [
    "Bachata",
    "Pop Latino"
  ],
  "album": {
    "nombre": "2000",
    "añoLanzamiento": 2023,
    "totalCanciones": 15
  }
}
```

------------------------------------------------------------------------

# 5. Errores comunes

## Error 1: Sin comillas dobles en las claves

❌ Incorrecto:

``` json
{
  titulo: "Tu canción favorita"
}
```

✅ Correcto:

``` json
{
  "titulo": "Tu canción favorita"
}
```

------------------------------------------------------------------------

## Error 2: Usar comillas simples

❌ Incorrecto:

``` json
{
  "titulo": 'Tu canción favorita'
}
```

✅ Correcto:

``` json
{
  "titulo": "Tu canción favorita"
}
```

------------------------------------------------------------------------

## Error 3: Coma al final

❌ Incorrecto:

``` json
{
  "titulo": "Waka Waka",
  "duracion": 200,
}
```

✅ Correcto:

``` json
{
  "titulo": "Waka Waka",
  "duracion": 200
}
```

------------------------------------------------------------------------

# 6. Dinámica: El Traductor

Reto:

Tengo esta clase en Java. Escriban el equivalente en formato JSON con valores de ejemplo.
``` java
public class Estudiante {

    private String nombre;
    private int codigo;
    private double notaFinal;

}
```

Respuesta:

``` json
{
  "nombre": "Tu Nombre Aquí",
  "codigo": 12345,
  "notaFinal": 4.5
}
```

------------------------------------------------------------------------

# Idea clave

``` text
Frontend  --->  JSON  --->  Backend
Backend   --->  JSON  --->  Frontend
```

JSON es el formato que permite que diferentes tecnologías puedan
comunicarse entre sí.
