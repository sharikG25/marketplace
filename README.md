# 🛒 Marketplace E-Commerce — Arquitectura de Microservicios RAD

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-6DB33F?style=flat&logo=spring)
![Java](https://img.shields.io/badge/Java-26-ED8B00?style=flat&logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat&logo=mysql)
![React](https://img.shields.io/badge/React-18-61DAFB?style=flat&logo=react)
![License](https://img.shields.io/badge/License-MIT-green)

Marketplace de comercio electrónico desarrollado con **metodología RAD** (Rapid Application Development), arquitectura de **microservicios** con Spring Boot 4.0, Java 26 y MySQL.

---

## 📋 Tabla de Contenidos

- [Descripción](#descripción)
- [Arquitectura](#arquitectura)
- [Microservicios](#microservicios)
- [Tecnologías](#tecnologías)
- [Requisitos previos](#requisitos-previos)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Endpoints principales](#endpoints-principales)
- [Variables de entorno](#variables-de-entorno)
- [Pruebas](#pruebas)
- [Despliegue en nube](#despliegue-en-nube)
- [Estructura del proyecto](#estructura-del-proyecto)

---

## 📖 Descripción

Sistema de marketplace que permite a compradores y vendedores interactuar en una plataforma de comercio electrónico con:

- 🔐 Autenticación segura con JWT y BCrypt
- 📦 Catálogo de productos con búsqueda semántica
- 📊 Inventario en tiempo real
- 🛒 Gestión de órdenes y carrito
- 💳 Procesamiento de pagos
- 📧 Notificaciones por email
- 📈 Analítica de ventas

---

## 🏗️ Arquitectura

```
Cliente (React)
      │
      ▼
API Gateway (:8080)
      │
      ├──► auth-service        (:8081) ──► auth_db
      ├──► user-service         (:8082) ──► user_db
      ├──► product-service      (:8083) ──► product_db
      ├──► inventory-service    (:8084) ──► inventory_db
      ├──► order-service        (:8085) ──► order_db
      ├──► payment-service      (:8086) ──► payment_db
      ├──► notification-service (:8087) ──► notification_db
      └──► analytics-service    (:8088) ──► analytics_db
```

---

## 🔧 Microservicios

| Servicio | Puerto | Responsabilidad |
|---|---|---|
| `api-gateway` | 8080 | Enrutamiento, CORS, punto de entrada |
| `auth-service` | 8081 | Login, registro, JWT, BCrypt |
| `user-service` | 8082 | Perfiles, roles, datos personales |
| `product-service` | 8083 | Catálogo, búsqueda semántica |
| `inventory-service` | 8084 | Stock en tiempo real, alertas |
| `order-service` | 8085 | Carrito, órdenes, estados |
| `payment-service` | 8086 | Transacciones, reembolsos |
| `notification-service` | 8087 | Emails automáticos |
| `analytics-service` | 8088 | Reportes de ventas |

---

## 💻 Tecnologías

| Capa | Tecnología | Versión |
|---|---|---|
| Backend | Spring Boot | 4.0.6 |
| Lenguaje | Java | 26.0.1 |
| Seguridad | Spring Security + JWT | 7.0.5 + 0.12.3 |
| Encriptación | BCrypt | Factor 12 |
| ORM | Hibernate | 7.2.12 |
| Base de datos | MySQL | 8.0+ |
| Gateway | Spring Cloud Gateway | 2024.0.1 |
| Frontend | React + Vite | 18+ |
| Build | Maven | 3.9.x |

---

## ✅ Requisitos Previos

Antes de ejecutar el proyecto necesitas:

- **Java 26** — [Descargar Temurin 26](https://adoptium.net/temurin/releases/?version=26)
- **Maven 3.9+** — [Descargar Maven](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** — Via [XAMPP](https://www.apachefriends.org) o instalación directa
- **IntelliJ IDEA** — [Descargar](https://www.jetbrains.com/idea/)
- **Postman** — [Descargar](https://www.postman.com/downloads/) para pruebas

---

## 🚀 Instalación y Ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/marketplace.git
cd marketplace
```

### 2. Crear las bases de datos en MySQL

```sql
CREATE DATABASE IF NOT EXISTS auth_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS user_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS product_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS inventory_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS order_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS payment_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS notification_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS analytics_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configurar variables de entorno

En cada `application.properties` actualiza:

```properties
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

### 4. Compilar todos los módulos

```bash
mvn clean install -DskipTests
```

### 5. Ejecutar los servicios en orden

```bash
# Terminal 1 — Auth Service
cd auth-service && mvn spring-boot:run

# Terminal 2 — User Service
cd user-service && mvn spring-boot:run

# Terminal 3 — Product Service
cd product-service && mvn spring-boot:run

# Terminal 4 — Inventory Service
cd inventory-service && mvn spring-boot:run

# Terminal 5 — Order Service
cd order-service && mvn spring-boot:run

# Terminal 6 — Payment Service
cd payment-service && mvn spring-boot:run

# Terminal 7 — Notification Service
cd notification-service && mvn spring-boot:run

# Terminal 8 — Analytics Service
cd analytics-service && mvn spring-boot:run

# Terminal 9 — API Gateway (último)
cd api-gateway && mvn spring-boot:run
```

---

## 📡 Endpoints Principales

Todos los endpoints van a través del **API Gateway** en `http://localhost:8080`

### Autenticación

```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "Juan Pérez",
  "email": "juan@test.com",
  "password": "123456"
}
```

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "juan@test.com",
  "password": "123456"
}
```

### Productos

```http
GET /api/products
GET /api/products/search?q=laptop
GET /api/products/category/tecnologia
POST /api/products
PUT /api/products/{id}
DELETE /api/products/{id}
```

### Órdenes

```http
POST /api/orders
GET /api/orders/user/{userId}
PATCH /api/orders/{id}/status?status=CONFIRMED
PATCH /api/orders/{id}/cancel
```

### Pagos

```http
POST /api/payments
GET /api/payments/order/{orderId}
PATCH /api/payments/{id}/refund
```

### Analítica

```http
GET /api/analytics/sales/by-category
GET /api/analytics/sales/top-products
GET /api/analytics/revenue/total
```

---

## 🔐 Variables de Entorno

**NUNCA subas estas variables al repositorio.** Usa un archivo `.env` local o configúralas en tu plataforma de despliegue.

| Variable | Servicio | Descripción |
|---|---|---|
| `DB_USERNAME` | Todos | Usuario MySQL |
| `DB_PASSWORD` | Todos | Contraseña MySQL |
| `JWT_SECRET` | auth-service | Clave secreta JWT (Base64) |
| `JWT_EXPIRATION` | auth-service | Expiración en ms (86400000 = 24h) |
| `MAIL_USERNAME` | notification-service | Email Gmail |
| `MAIL_PASSWORD` | notification-service | App Password Gmail |

---

## 🧪 Pruebas

### Pruebas con Postman

Importa la colección de Postman incluida en `/docs/Marketplace.postman_collection.json`

### Pruebas unitarias con JUnit

```bash
# Ejecutar pruebas de un servicio
cd auth-service
mvn test

# Ejecutar pruebas de todos los servicios
mvn test --projects auth-service,product-service,order-service
```

---

## ☁️ Despliegue en Nube

### Backend — Render

1. Crear Web Service en [render.com](https://render.com)
2. Conectar repositorio GitHub
3. Build Command: `mvn clean package -DskipTests`
4. Start Command: `java -jar target/auth-service-0.0.1-SNAPSHOT.jar`
5. Agregar variables de entorno en el dashboard

### Frontend — Vercel

```bash
cd frontend
npm install
npm run build
vercel deploy
```

### Base de datos — Railway

1. Crear proyecto en [railway.app](https://railway.app)
2. Agregar servicio MySQL
3. Copiar la URL de conexión al `application.properties`

---

## 📁 Estructura del Proyecto

```
marketplace/
├── pom.xml                      # POM padre - módulos registrados
├── .gitignore
├── README.md
│
├── api-gateway/                 # Spring Cloud Gateway
│   ├── pom.xml
│   └── src/main/resources/
│       └── application.properties
│
├── auth-service/                # Autenticación JWT + BCrypt
│   ├── pom.xml
│   └── src/main/java/com/marketplace/authservice/
│       ├── config/SecurityConfig.java
│       ├── controller/AuthController.java
│       ├── dto/{LoginRequest, RegisterRequest, AuthResponse}.java
│       ├── entity/User.java
│       ├── repository/UserRepository.java
│       └── service/{AuthService, JwtService, UserDetailsImpl}.java
│
├── product-service/             # Catálogo y búsqueda
├── inventory-service/           # Stock en tiempo real
├── order-service/               # Órdenes y carrito
├── payment-service/             # Pagos y transacciones
├── notification-service/        # Emails automáticos
├── analytics-service/           # Reportes de ventas
├── user-service/                # Perfiles y roles
│
└── frontend/                    # React + Vite
    ├── src/
    │   ├── api/
    │   ├── components/
    │   ├── pages/
    │   └── store/
    └── package.json
```

---

## 🤝 Roles y Permisos

| Rol | Descripción | Permisos |
|---|---|---|
| `CUSTOMER` | Comprador registrado | Ver productos, crear órdenes, pagar |
| `SELLER` | Vendedor | Gestionar productos e inventario |
| `ADMIN` | Administrador | Acceso total al sistema |

---

## 📚 Referencias

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JWT Introduction](https://jwt.io/introduction)
- Pressman, R. S. (2010). *Ingeniería del Software: Un enfoque práctico* (7ma ed.). McGraw-Hill.

---

## 📄 Licencia

MIT License — ver [LICENSE](LICENSE) para más detalles.

---

*Proyecto desarrollado como parte de la Actividad 2 — Metodología RAD — Campo Disciplinar Desarrollo de Software 2026*
