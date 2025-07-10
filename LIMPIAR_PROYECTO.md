# 🧹 Limpiar Proyecto - Eliminar Archivos Duplicados

## ❗ **Problema identificado:**

Tienes **código de 2 aplicaciones diferentes** en el mismo proyecto:

### ✅ **App ACTIVA (que funciona):**
- **Ubicación:** `app/src/main/java/com/example/nutritiontracker/`
- **Namespace:** `com.example.nutritiontracker`
- **Configurada en:** AndroidManifest.xml
- **Estado:** ✅ Completamente funcional con Compose + Room Database

### ❌ **App DUPLICADA (no se usa):**
- **Ubicación:** `app/src/main/kotlin/com/example/weightlossapp/`
- **Namespace:** `com.example.weightlossapp`
- **Estado:** ❌ No se usa, causa confusión

## 🗑️ **Archivos a ELIMINAR:**

### **Eliminar TODO el directorio:**
```
app/src/main/kotlin/
```

### **Específicamente estos archivos:**
```
app/src/main/kotlin/com/example/weightloss/
├── App.kt

app/src/main/kotlin/com/example/weightlossapp/
├── MainActivity.kt
├── navigation/
├── data/
└── ui/
```

## 🔧 **Cómo limpiar en Android Studio:**

### **Opción 1: Desde Android Studio**
1. En **Project Explorer**, navega a `app/src/main/`
2. **Click derecho** en el directorio `kotlin/`
3. Selecciona **"Delete"**
4. Confirma **"Safe Delete"**

### **Opción 2: Desde explorador de archivos**
1. Navega a tu proyecto
2. Ve a `app/src/main/`
3. Elimina toda la carpeta `kotlin/`

### **Opción 3: Comando terminal**
```bash
# En la raíz de tu proyecto:
rm -rf app/src/main/kotlin/
```

## ✅ **Después de limpiar:**

### **Estructura correcta:**
```
app/src/main/
├── java/com/example/nutritiontracker/     ✅ TU APP
│   ├── MainActivity.kt                    ✅ Compose + Room
│   ├── NutritionViewModel.kt              ✅ ViewModel
│   ├── database/                          ✅ Room Database
│   └── repository/                        ✅ Repository
├── res/                                   ✅ Recursos
└── AndroidManifest.xml                    ✅ Configuración
```

## 🚀 **Beneficios de limpiar:**

- ✅ **No más confusión** sobre qué MainActivity usar
- ✅ **Proyecto más limpio** y organizado
- ✅ **Menos archivos** = compilación más rápida
- ✅ **Fácil navegación** en Android Studio
- ✅ **Sin conflictos** de nombres

## ⚠️ **Importante:**

- **NO toques** el directorio `java/com/example/nutritiontracker/` ✅
- **Solo elimina** el directorio `kotlin/` ❌
- Tu app seguirá funcionando perfectamente después de limpiar

## 🎯 **Después de limpiar:**

1. **Sync Project** en Android Studio
2. **Clean Project** (Build → Clean Project)
3. **Rebuild Project** (Build → Rebuild Project)
4. **¡Listo!** Tu proyecto estará limpio y funcionando

## 📋 **Resumen:**

**ANTES:** 2 MainActivity confusas 😵  
**DESPUÉS:** 1 MainActivity clara ✅  

**ELIMINAR:** `app/src/main/kotlin/` (todo el directorio)  
**MANTENER:** `app/src/main/java/com/example/nutritiontracker/` ✅