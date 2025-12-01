# Aanwezigheid Logger

Een Spring Boot applicatie voor het bijhouden van studentenaanwezigheden met JWT authenticatie.


## Overzicht

Deze applicatie biedt een systeem voor het registreren en beheren van studentenaanwezigheden. Gebruikers kunnen inloggen, aanwezigheden registreren en exporteren naar CSV-formaat.

## Functionaliteiten

-  **Authenticatie & Autorisatie** - JWT-gebaseerde beveiliging
-  **Aanwezigheid Registratie** - Registreer student aanwezigheden met tijdstempel
-  **CSV Export** - Exporteer aanwezigheidsgegevens naar CSV
-  **REST API** - Volledig RESTful API voor frontend integratie

## Technologieën

### Backend
- **Java 17**
- **Spring Boot 3.x**
  - Spring Web
  - Spring Data JPA
  - Spring Security
- **MySQL** - Database
- **JSON Web Tokens** - Authenticatie
- **OpenCSV** - CSV export functionaliteit
- **Dotenv Java** - Environment variables beheer

### Frontend
- **HTML/CSS/JavaScript**
- **Vite** - Build tool en development server

## Installatie

### Vereisten

- Java 17 of hoger
- MySQL 8.0 of hoger
- Node.js en npm (voor frontend)
- Maven (of gebruik de meegeleverde Maven Wrapper)

### Stap 1: Clone de Repository

```bash
git clone <repository-url>
cd aanwezigheid-logger
```

### Stap 2: Database Setup

1. Start MySQL server
2. Maak de database aan via de gegeven structuur


### Stap 3: Environment Variables

1. Kopieer het voorbeeld bestand:
```bash
copy .env.example .env
```

2. Pas de waardes aan in `.env`:
```env
DB_URL=jdbc:mysql://localhost:3306/aanwezigheid_logger
DB_USERNAME=root
DB_PASSWORD=jouw_wachtwoord
SERVER_PORT=8080
JWT_SECRET=verander_dit_naar_een_veilige_geheime_sleutel_van_minimaal_256_bits
JWT_EXPIRATION=86400000
JPA_DDL_AUTO=update
JPA_SHOW_SQL=true
```

**Belangrijk**: Verander `JWT_SECRET` naar een veilige, willekeurige string!

### Stap 4: Backend Installatie

```bash
# Installeer dependencies en build
.\mvnw.cmd clean install

# Start de applicatie
.\mvnw.cmd spring-boot:run
```

De backend draait nu op `http://localhost:8080`

### Stap 5: Frontend Installatie

```bash
cd frontend

# Installeer dependencies
npm install

# Start development server
npm run dev
```

De frontend draait nu op `http://localhost:5173`

## Configuratie

### Application Properties

De applicatie gebruikt environment variables voor configuratie. Zie `application.properties`:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
server.port=${SERVER_PORT}
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}
```

##  Frontend

De frontend is gebouwd met vanilla JavaScript en Vite voor een snelle development ervaring.

### Structuur
```
frontend/
├── index.html          # Hoofd HTML bestand
├── package.json        # NPM dependencies
├── vite.config.js      # Vite configuratie
├── public/            # Statische assets
└── src/
    ├── main.js        # JavaScript entry point
    └── style.css      # Styling
```

### Development

```bash
cd frontend
npm run dev          # Start dev server
npm run build        # Build voor productie
npm run preview      # Preview productie build
```

## Beveiliging

### JWT Tokens

- Tokens verlopen na 24 uur (configureerbaar via `JWT_EXPIRATION`)
- Tokens bevatten gebruikersnaam en rollen
- Alle beschermde endpoints vereisen een geldig token in de `Authorization` header

### Wachtwoorden

- Wachtwoorden worden opgeslagen als BCrypt hash
- Minimum beveiliging via Spring Security

### Environment Variables

- Gevoelige data (wachtwoorden, secrets) worden opgeslagen in `.env`
- `.env` bestand staat in `.gitignore` en wordt NIET gecommit

##  Auteur

Brend Van den Eynde



