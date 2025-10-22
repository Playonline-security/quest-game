# 🌳 El bosque del eco: Aventura de texto con Java Web

Una aventura de texto inmersiva donde tus decisiones forjan tu destino. Este proyecto fue desarrollado con **Java Servlets, JSP y JSTL**, creando una experiencia de juego dinámica y persistente directamente en tu navegador.

---

### ✨ Características principales

* 📖 **Narrativa dinámica:** Explora múltiples caminos y descubre varios finales basados en tus elecciones.
* 👤 **Perfil de jugador:** Dale un nombre personalizado a tu personaje que se guarda durante tu sesión.
* 📊 **Seguimiento de estadísticas:** El juego rastrea tus **victorias, derrotas y partidas jugadas**.
* 🎨 **Interfaz inmersiva:** Un diseño visual con imágenes y video para mejorar la ambientación.
* 🧪 **Lógica probada:** Cobertura de pruebas unitarias con **JUnit 5** para garantizar la fiabilidad de cada rama de la historia.

---

### 🛠️ Stack tecnológico

| Categoría      | Tecnología                                      |
| :------------- | :---------------------------------------------- |
| **Backend** | Java 17, Servlets 4.0, JSP 2.3, JSTL 1.2          |
| **Build Tool** | Maven                                           |
| **Servidor Web** | Apache Tomcat 9                                 |
| **Pruebas** | JUnit 5                                         |

---

### 🚀 Cómo empezar

Sigue estos pasos para ejecutar el proyecto localmente:

1.  **Clona el repositorio**
    ```bash
    git clone https://github.com/Playonline-security/quest-game.git
    cd quest-game
    ```

2.  **Construye con maven**
    Este comando compilará el proyecto y creará un archivo `.war`.
    ```bash
    mvn clean install
    ```

3.  **Despliega en Tomcat**
    * Copia el archivo `target/QuestGame.war`.
    * Pégalo en el directorio `webapps/` de tu instalación de Tomcat.

4.  **¡Juega!**
    * Inicia tu servidor Tomcat.
    * Abre tu navegador y ve a: `http://localhost:8080/QuestGame/`

---

### 👤 Autor

* **Jonathan Uzcátegui González**

_Este proyecto fue desarrollado como parte del programa de formación de CodeGym._
