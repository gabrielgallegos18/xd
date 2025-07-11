# 📱 Nutrition Tracker - Seguimiento Nutricional

Una aplicación Android moderna desarrollada con **Jetpack Compose** para el seguimiento diario de calorías y nutrición.

## ✨ Características

- **Seguimiento de calorías diario** - Registra todos tus alimentos del día
- **Base de datos de alimentos** - Más de 10 alimentos comunes con información nutricional
- **Búsqueda inteligente** - Encuentra alimentos por nombre o categoría
- **Tipos de comida** - Clasifica por desayuno, almuerzo, cena y snacks
- **Interfaz moderna** - Material Design 3 con Jetpack Compose
- **Cálculos automáticos** - Total de calorías y macronutrientes

## 🔧 Tecnologías

- **Kotlin** - Lenguaje principal
- **Jetpack Compose** - UI moderna y declarativa
- **Material Design 3** - Tema y componentes modernos
- **Android Architecture Components** - Mejores prácticas
- **Gradle** - Sistema de construcción

## 🚀 Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/tuusuario/nutrition-tracker.git
```

2. Abre el proyecto en Android Studio

3. Sincroniza el proyecto con Gradle

4. Ejecuta la aplicación en un dispositivo o emulador

## 📱 Cómo usar

1. **Agregar alimentos**: Toca el botón "Agregar Alimento"
2. **Buscar**: Escribe el nombre del alimento en el campo de búsqueda
3. **Seleccionar**: Toca el alimento deseado de la lista
4. **Configurar**: Ajusta la cantidad y tipo de comida
5. **Confirmar**: Toca "Agregar" para registrar el alimento

## 🏗️ Arquitectura

```
app/
├── src/main/kotlin/com/example/nutritiontracker/
│   ├── MainActivity.kt                 # Actividad principal
│   ├── data/                          # Modelos y repositorio
│   │   ├── Food.kt                    # Modelo de alimento
│   │   ├── FoodEntry.kt               # Modelo de entrada de comida
│   │   └── FoodRepository.kt          # Repositorio de datos
│   └── ui/                            # Interfaz de usuario
│       ├── screens/                   # Pantallas
│       │   └── NutritionTrackerApp.kt # Pantalla principal
│       └── theme/                     # Tema y estilos
│           ├── Color.kt               # Colores
│           ├── Theme.kt               # Tema principal
│           └── Type.kt                # Tipografía
```

## 🎨 Capturas de pantalla

- Pantalla principal con resumen de calorías
- Diálogo para agregar alimentos
- Lista de alimentos del día
- Búsqueda y selección de alimentos

## 🔄 Versiones

- **v1.0.0** - Versión inicial con seguimiento básico de calorías

## 📝 Notas de desarrollo

- Utiliza estado local para persistencia simple
- Implementa repositorio en memoria
- Sigue principios de Material Design 3
- Código limpio y bien estructurado

## 🤝 Contribuir

Las contribuciones son bienvenidas. Para cambios importantes:

1. Crea un fork del proyecto
2. Crea una rama para tu feature
3. Realiza tus cambios
4. Envía un pull request

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ve el archivo [LICENSE](LICENSE) para más detalles.

---

**Desarrollado con ❤️ usando Jetpack Compose**
