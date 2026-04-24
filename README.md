# Flight Simulator Challenge

This repository contains a **monorepo** with a backend service and a frontend application that together simulate a flight and live metric updates.

The project is designed to demonstrate full-stack engineering skills, including:

- backend API design
- persistent data storage
- simulation logic
- frontend visualization
- Docker-based local setup
- clean architecture and code organization

---

## Project Overview

The application simulates a commercial flight and exposes flight status, metrics, and history through a REST API.

The frontend visualizes the flight as an airplane moving along a curved route, while the backend is responsible for generating and persisting the simulation data.

The solution is intentionally modular so that the backend can evolve independently from the frontend and both can scale with minimal coupling.

---

## Monorepo Structure


### `backend/`
Kotlin + Spring Boot service responsible for:

- creating flights
- simulating flight progression
- persisting flight data and metrics
- exposing REST endpoints
- providing flight history

### `frontend/`
Next.js + React application responsible for:

- displaying the flight route
- animating the airplane path
- showing live metrics
- rendering phase progress and status updates

---

## Tech Stack

### Backend
- Kotlin
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Validation
- Scheduling

### Frontend
- Next.js
- React
- TypeScript
- Tailwind CSS

### Infrastructure
- Docker
- Docker Compose

---

## Main Features

- Flight creation through API
- Persistent storage for flights and metrics
- Automatic flight progression simulation
- route visualization
- Live-ish frontend updates through polling
- Flight history retrieval
- Containerized local development environment

---

## Local Development

### Prerequisites
- Java 17
- Node.js 20+
- Docker Desktop
- Git

---

## Run the Database

The project uses PostgreSQL through Docker Compose.

Start the database with:


This starts PostgreSQL locally and exposes it on port `5432`.

---

## Run the Backend

From the `backend/` directory:

``` 
The backend should start on:
```

text http://localhost:8080``` 

---

## Run the Frontend

From the `frontend/` directory:
```

bash npm install npm run dev``` 

The frontend should start on:
```

text http://localhost:3000``` 

---

## Environment Configuration

### Frontend
Create a `.env.local` file inside `frontend/`:
```

env NEXT_PUBLIC_API_URL=http://localhost:8080``` 

### Backend
Backend configuration is managed through Spring Boot profile files such as:

- `application.yml`
- `application-dev.yml`
- `application-test.yml`

---

## API Endpoints

### Create a flight
```

http POST /flights``` 

### List flights
```

http GET /flights``` 

### Get current flight status
```

http GET /flights/{id}``` 

### Get flight history
```

http GET /flights/{id}/history``` 

---

## Frontend Behavior

The frontend includes:

- a non-linear SVG-based flight path
- an airplane moving from origin to destination
- a metrics panel with live flight data
- a timeline showing the current flight phase
- polling against the backend to keep the UI updated

---

## Backend Behavior

The backend:

- stores flights in PostgreSQL
- generates flight metrics over time
- advances active flights automatically through scheduling
- updates the current flight phase and progress
- persists historical metrics for later retrieval

---

## Branching Strategy

Recommended branches:

- `main` → stable version
- `develop` → integration branch
- `feature/backend-*` → backend work
- `feature/frontend-*` → frontend work
- `feature/docker-*` → containerization

