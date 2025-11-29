# Aanwezigheid Logger — API Documentatie

---

## Authenticatie Endpoints

### POST `/api/auth/login`

**Beschrijving:** Logt een gebruiker in en retourneert een JWT token.

**Request:**
```json
{
  "username": "admin",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "admin",
  "role": "ADMIN"
}
```

**Errors:**
- `401 Unauthorized` - Verkeerde credentials
- `400 Bad Request` - Missende velden
---

### POST `/api/auth/register`

**Beschrijving:** Registreert een nieuwe gebruiker als ADMIN. **Vereist authenticatie!**

**BELANGRIJK:** Dit endpoint is beveiligd. Je moet ingelogd zijn met een geldig JWT token om nieuwe gebruikers te kunnen aanmaken.

**Headers:**
```
Authorization: Bearer <token>
```

**Request:**
```json
{
  "username": "docent1",
  "password": "password123"
}
```

**Response (200 OK):**
```
Gebruiker geregistreerd: docent1 (ADMIN)
```

**Validatie:**
- Username is verplicht en moet uniek zijn
- Wachtwoord minimaal 6 karakters

**Errors:**
- `401 Unauthorized` - Geen token of ongeldige token
- `400 Bad Request` - Username bestaat al of validatie gefaald

---

## Aanwezigheid Endpoints

**Alle aanwezigheid endpoints vereisen authenticatie!**

Voeg bij elke request toe:
```
Authorization: Bearer <token>
```
---

### POST `/api/aanwezigheid`

**Beschrijving:** Registreert een aanwezigheid voor een student op basis van stamnummer.

**Headers:**
```
Authorization: Bearer <token>
Content-Type: application/json
```

**Request:**
```json
{
  "stamnr": "12345",
  "lesOfProject": "Java Programming"
}
```

**Velden:**
- `stamnr` (verplicht) - Student stamnummer
- `lesOfProject` (optioneel) - Naam van de les of project

**Response (200 OK):**
```json
{
  "id": 1,
  "studentId": 5,
  "timestamp": "2024-11-26T14:30:00",
  "lesOfProject": "Java Programming",
  "opmerking": null
}
```

**Gedrag:**
1. Zoekt student op basis van stamnummer
2. Als student niet bestaat → Error 404
3. Als student bestaat → Maakt aanwezigheid aan met huidige timestamp
4. Slaat op in database en retourneert het object

**Errors:**
- `400 Bad Request` - Stamnummer is leeg of niet meegegeven
- `404 Not Found` - Student met dit stamnummer bestaat niet
- `401 Unauthorized` - Geen token of ongeldige token


---

### GET `/api/aanwezigheid`

**Beschrijving:** Haalt alle aanwezigheden op.

**Headers:**
```
Authorization: Bearer <token>
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "studentId": 5,
    "timestamp": "2024-11-26T10:00:00",
    "lesOfProject": "Java Programming",
    "opmerking": null
  },
  {
    "id": 2,
    "studentId": 6,
    "timestamp": "2024-11-26T10:05:00",
    "lesOfProject": "Databases",
    "opmerking": null
  }
]
```

---

### GET `/api/aanwezigheid/dag/{datum}`

**Beschrijving:** Haalt aanwezigheden op voor een specifieke dag.

**Path Parameter:**
- `datum` - Formaat: `YYYY-MM-DD` (bijvoorbeeld: `2025-11-26`)

**Headers:**
```
Authorization: Bearer <token>
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "studentId": 5,
    "timestamp": "2024-11-26T10:00:00",
    "lesOfProject": "Java Programming",
    "opmerking": null
  }
]
```

**Gedrag:**
- Haalt alle aanwezigheden tussen 00:00:00 en 23:59:59 van de opgegeven datum

**Errors:**
- `400 Bad Request` - Ongeldige datum formaat
- `401 Unauthorized` - Geen token of ongeldige token

---

## CSV Export Endpoints

### GET `/api/aanwezigheid/export`

**Beschrijving:** Exporteert alle aanwezigheden als CSV bestand.

**Headers:**
```
Authorization: Bearer <token>
```

**Response (200 OK):**
- Content-Type: `text/csv`

**CSV Kolommen:**
```
ID, Student ID, Stamnr, Naam, Voornaam, Email, Timestamp, Les/Project, Opmerking
```

**Voorbeeld CSV output:**
```csv
ID,Student ID,Stamnr,Naam,Voornaam,Email,Timestamp,Les/Project,Opmerking
1,5,12345,Peeters,Jan,jan.peeters@student.be,2024-11-26 10:00:00,Java Programming,
2,6,12346,Janssens,Lisa,lisa.janssens@student.be,2024-11-26 10:05:00,Databases,
```

---

### GET `/api/aanwezigheid/export/dag/{datum}`

**Beschrijving:** Exporteert aanwezigheden van een specifieke dag als CSV bestand.

**Path Parameter:**
- `datum` - Formaat: `YYYY-MM-DD`

**Headers:**
```
Authorization: Bearer <token>
```

**Response (200 OK):**
- Content-Type: `text/csv`


---
