# Parte 1 — API REST de Reservas

## Objetivo

Construir una API REST que permita gestionar las reservas de salas de estudio mediante operaciones CRUD.

La aplicación deberá permitir:

- Consultar todas las reservas.
- Consultar una reserva específica.
- Crear una nueva reserva.
- Actualizar una reserva.
- Eliminar una reserva.

El proyecto deberá mantener separadas las responsabilidades de cada capa:

### Model

Representa la información de una reserva.

### Repository

Se encarga de administrar las reservas almacenadas por la aplicación.

### Service

Centraliza las operaciones relacionadas con las reservas y comunica el Controller con el Repository.

### Controller

Recibe las peticiones HTTP, obtiene la información necesaria y delega las operaciones al Service.

---

# 1. Modelo `Reserva`

Crear una clase `Reserva` con los siguientes atributos:

| Atributo     | Tipo     |
| ------------ | -------- |
| `id`         | `Long`   |
| `estudiante` | `String` |
| `sala`       | `String` |
| `hora`       | `String` |

### Ejemplo de una reserva

```json
{
  "id": 1,
  "estudiante": "Laura",
  "sala": "Sala A",
  "hora": "10:00"
}
```

La clase deberá contar con:

- Constructor vacío.
- Constructor con todos los atributos.
- Getters.
- Setters.

---

# 2. Repository

Crear `ReservaRepository`.

El Repository deberá mantener una colección de reservas y proporcionar las operaciones necesarias para:

- Obtener todas las reservas.
- Buscar una reserva por su identificador.
- Guardar una reserva.
- Actualizar una reserva.
- Eliminar una reserva.

La colección será administrada directamente por el Repository.

> El Controller no deberá acceder directamente a la colección.

---

# 3. Service

Crear `ReservaService`.

El Service deberá comunicarse con `ReservaRepository` para realizar las operaciones sobre las reservas.

Debe proporcionar métodos para:

- Obtener todas las reservas.
- Obtener una reserva por su identificador.
- Crear una reserva.
- Actualizar una reserva.
- Eliminar una reserva.

El flujo de las operaciones deberá mantenerse:

```text
Controller
    ↓
Service
    ↓
Repository
```

---

# 4. Controller

Crear `ReservaController`.

Utilizar:

```java
@RestController
```

y establecer como ruta base:

```java
@RequestMapping("/reservas")
```

El Controller deberá implementar las siguientes operaciones.

## Consultar todas las reservas

Utilizar:

```java
@GetMapping
```

La operación deberá retornar todas las reservas.

---

## Consultar una reserva

Utilizar:

```java
@GetMapping("/{id}")
```

El identificador deberá recibirse mediante:

```java
@PathVariable
```

Por ejemplo:

```text
/reservas/5
```

El valor `5` corresponde al identificador de la reserva que se desea consultar.

---

## Crear una reserva

Utilizar:

```java
@PostMapping
```

La información de la reserva deberá recibirse mediante:

```java
@RequestBody
```

### Ejemplo del cuerpo de la petición

```json
{
  "id": 1,
  "estudiante": "Laura",
  "sala": "Sala A",
  "hora": "10:00"
}
```

El Controller deberá enviar la información recibida al Service.

---

## Actualizar una reserva

Utilizar:

```java
@PutMapping("/{id}")
```

Esta operación deberá utilizar:

```java
@PathVariable
```

para identificar la reserva que se desea modificar.

También deberá utilizar:

```java
@RequestBody
```

para recibir los nuevos datos.

Por ejemplo:

```text
PUT /reservas/1
```

con:

```json
{
  "estudiante": "Laura Pérez",
  "sala": "Sala B",
  "hora": "14:00"
}
```

El identificador de la reserva será obtenido desde la URL.

Los demás datos serán obtenidos desde el cuerpo de la petición.

---

## Eliminar una reserva

Utilizar:

```java
@DeleteMapping("/{id}")
```

El identificador de la reserva deberá recibirse mediante:

```java
@PathVariable
```

Por ejemplo:

```text
DELETE /reservas/3
```

# Parte 2 — Una nueva necesidad del sistema

La API ya permite gestionar las reservas de las salas.

Después de comenzar a utilizar el sistema, la universidad identifica una situación que debe ser controlada:

> Dos estudiantes no pueden reservar la misma sala para la misma hora.

Actualmente podría ocurrir lo siguiente:

### Primera reserva

```json
{
  "id": 1,
  "estudiante": "Laura",
  "sala": "Sala A",
  "hora": "10:00"
}
```

Posteriormente, otro estudiante podría intentar registrar:

```json
{
  "id": 2,
  "estudiante": "Carlos",
  "sala": "Sala A",
  "hora": "10:00"
}
```

La segunda reserva **no debería ser aceptada** porque la Sala A ya está reservada a las 10:00.

Sin embargo, una reserva de la misma sala para otra hora sí debe ser posible.

Por ejemplo:

```json
{
  "id": 2,
  "estudiante": "Carlos",
  "sala": "Sala A",
  "hora": "11:00"
}
```

Esta reserva puede realizarse porque corresponde a un horario diferente.

---

# 7. Nueva regla de negocio

Agregar al sistema la siguiente regla:

> Una sala no puede tener más de una reserva para la misma hora.

La regla debe aplicarse **antes de guardar una nueva reserva**.

El flujo esperado será:

```text
POST /reservas
       ↓
   Controller
       ↓
     Service
       ↓
¿Sala y hora disponibles?
       ↓
   ┌───┴───┐
   │       │
  SÍ       NO
   │       │
   ▼       ▼
Repository  No guardar
   │
   ▼
Guardar
```
