## 🚨 Helper repo

⚠️ This repo is not part of the main project.

# ScholarAI Ongoing Research Service

A Spring Boot microservice for managing ongoing research projects with LaTeX document editing, AI assistance, and PDF generation capabilities.

## Features

- **LaTeX Document Management**: Create, edit, and compile LaTeX documents
- **AI-Powered Assistance**: Writing suggestions, document review, and compliance checking
- **PDF Generation**: Convert LaTeX documents to PDF using Pandoc
- **Project Management**: Organize research projects with status tracking
- **Template System**: Pre-built templates for different document types
- **Citation Management**: Track and validate academic citations
- **Real-time Compilation**: Live LaTeX to HTML preview

## Technology Stack

- **Backend**: Spring Boot 3.5.0, Java 21
- **Database**: H2 (in-memory), JPA/Hibernate
- **Build Tool**: Maven
- **LaTeX Compilation**: Pandoc with fallback HTML conversion
- **API Documentation**: RESTful endpoints with JSON responses

## Prerequisites

- Java 21 or later
- Maven (included with the project)
- Pandoc (optional, for enhanced LaTeX compilation)

## Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/Javafest2025/ongoing_research_service.git
cd ongoing_research_service
```

### 2. Run the Application
```bash
./mvnw spring-boot:run
```

The service will start on `http://localhost:8083`

### 3. Access the Application
- **API Base URL**: `http://localhost:8083/api`
- **H2 Console**: `http://localhost:8083/h2-console`
- **Health Check**: `http://localhost:8083/actuator/health`

## Configuration

### Pandoc Configuration
The service uses Pandoc for LaTeX compilation. If Pandoc is not in your PATH, specify the custom path:

```bash
# Using system property
./mvnw spring-boot:run -Dpandoc.path="/path/to/pandoc"

# Using environment variable
export PANDOC_PATH=/path/to/pandoc
./mvnw spring-boot:run
```

### Application Properties
Key configuration in `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8083

# Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

## API Endpoints

### Documents
- `POST /api/documents/compile` - Compile LaTeX to HTML
- `POST /api/documents/generate-pdf` - Generate PDF from LaTeX
- `GET /api/documents/project/{projectId}` - Get documents by project
- `POST /api/documents` - Create new document
- `PUT /api/documents/{documentId}` - Update document

### Projects
- `GET /api/projects/user/{userId}` - Get projects by user
- `POST /api/projects` - Create new project
- `PUT /api/projects/{projectId}` - Update project

### AI Assistance
- `POST /api/ai/review` - Review document with AI
- `POST /api/ai/suggestions` - Get writing suggestions
- `POST /api/ai/compliance` - Check document compliance
- `POST /api/ai/citations` - Validate citations
- `POST /api/ai/corrections` - Apply AI corrections

### Templates
- `GET /api/templates` - Get all templates
- `GET /api/templates/{templateId}` - Get template by ID
- `POST /api/templates` - Create new template
- `DELETE /api/templates/{templateId}` - Delete template

## Project Structure

```
src/main/java/org/solace/scholar_ai/ongoing_research_service/
├── controller/          # REST API controllers
├── service/            # Business logic services
├── repository/         # Data access layer
├── domain/entity/      # JPA entities
├── dto/               # Data transfer objects
│   ├── request/       # Request DTOs
│   ├── response/      # Response DTOs
│   └── common/        # Common DTOs
└── mapper/            # MapStruct mappers
```

## Development

### Building the Project
```bash
./mvnw clean compile
```

### Running Tests
```bash
./mvnw test
```

### Code Formatting
```bash
./mvnw spotless:apply
```

### Running with Custom Profile
```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

## Docker Support

### Building Docker Image
```bash
docker build -t scholarai-ongoing-research .
```

### Running with Docker
```bash
docker run -p 8083:8083 scholarai-ongoing-research
```

## Integration

This service is designed to work with other ScholarAI microservices:
- **Frontend**: [scholar-ai-frontend](https://github.com/Javafest2025/frontend)
- **User Service**: [scholar-ai-user-service](https://github.com/Javafest2025/user_service)

## Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   # Change port in application.properties
   server.port=8084
   ```

2. **Pandoc Not Found**
   - Install Pandoc: https://pandoc.org/installing.html
   - Or use fallback HTML compilation (automatic)

3. **Database Connection Issues**
   - Check H2 console at `http://localhost:8083/h2-console`
   - Verify database configuration in `application.properties`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Team

- **Organization**: [Javafest2025](https://github.com/Javafest2025)
- **Project**: ScholarAI - Team Solace
- **Contact**: trisn.eclipse@gmail.com
