# Platon API

## Variante Implementada
De acuerdo con mi número de documento (1038769684), implementé la **Variante A**. Esta variante incluye la creación de un servicio para gestionar cursos con las siguientes funcionalidades:
- Registrar un nuevo curso.
- Listar todos los cursos.
- Buscar cursos por facultad.
- Validaciones de negocio:
  - No permitir crear cursos con el mismo código.
  - Verificar que el cupo máximo sea mayor que cero.

## Cómo Probar el Servicio

### Requisitos Previos
1. Tener instalado y configurado Apache Tomcat (versión 9 o superior).
2. Desplegar el proyecto en el servidor Tomcat.
3. Asegurarse de que el servidor esté corriendo en el puerto `8080` (o el puerto configurado).

### Endpoints Disponibles

### 1. Registrar un Nuevo Curso
- **URL**: `POST /PlatonApi/cursos`
- **Formato de Entrada (JSON)**:
  ```json
  {
      "id": 1,
      "nombre": "Matemáticas Avanzadas",
      "codigo": "MAT101",
      "profesor": "Dr. Juan Pérez",
      "cupoMaximo": 30,
      "estudiantesInscritos": 0,
      "facultad": "Ciencias"
  }

### Respuesta Exitosa:
{
    "message": "Curso creado exitosamente"
}
#### Errores Posibles:
Código duplicado:
{
    "error": "Ya existe un curso con el mismo código."
}
Cupo máximo inválido:
{
    "error": "El cupo máximo debe ser mayor que cero."
}
#### 2. Listar Todos los Cursos
URL: GET /PlatonApi/cursos
Respuesta Exitosa:
[
    {
        "id": 1,
        "nombre": "Matemáticas Avanzadas",
        "codigo": "MAT101",
        "profesor": "Dr. Juan Pérez",
        "cupoMaximo": 30,
        "estudiantesInscritos": 0,
        "facultad": "Ciencias"
    }
]
#### 3. Buscar Cursos por Facultad
URL: GET /PlatonApi/cursos/facultad?nombre={facultad}
Parámetro:
nombre: Nombre de la facultad a buscar.
Respuesta Exitosa:
[
    {
        "id": 1,
        "nombre": "Matemáticas Avanzadas",
        "codigo": "MAT101",
        "profesor": "Dr. Juan Pérez",
        "cupoMaximo": 30,
        "estudiantesInscritos": 0,
        "facultad": "Ciencias"
    }
]
### Errores Posibles:
Parámetro faltante:
{
    "error": "Parámetro 'nombre' es requerido"
}
Notas Adicionales
Puedes usar herramientas como Postman para probar los endpoints.
