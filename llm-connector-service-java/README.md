# 🧠 LLM Connector Service (Java + Spring Boot)

This microservice provides a simple REST API to connect with LLMs like OpenAI or Hugging Face. It accepts a prompt and returns the model's response.

---

## 🚀 Features

- ✅ REST Endpoint: `/llm/query`
- ✅ Supports OpenAI & Hugging Face Inference APIs
- ✅ API Key-based Authentication
- ✅ Error Handling: timeouts, rate limits, quota errors
- ✅ Built with Java 17 & Spring Boot 3

---

## 📦 Prerequisites

- Java 17+
- Maven 3.8+
- Internet access (for calling LLM APIs)
- A valid API key from OpenAI or Hugging Face

---

## 🛠️ Build and Run

### 1. Clone the repo

```bash
git clone https://github.com/your-org/llm-connector-service-java.git
cd llm-connector-service-java
```

### 2. Configure your API keys

Edit the `src/main/resources/application.properties` file:

```properties
# === OpenAI ===
openai.api.key=sk-<your-openai-api-key>
openai.model=gpt-3.5-turbo

# === Hugging Face ===
huggingface.api.key=hf_<your-hf-token>
huggingface.model.url=https://api-inference.huggingface.co/models/microsoft/DialoGPT-medium
```

> 🔐 Use environment variables for production.

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the service

```bash
mvn spring-boot:run
```

The app will start on:  
**http://localhost:8080**

---

## 🧪 Test the API

### ▶️ POST `/llm/query`

#### Request:

```bash
curl -X POST http://localhost:8080/llm/query \
-H "Content-Type: application/json" \
-d '{"prompt": "Tell me a joke", "provider": "openai"}'
```

```bash
curl -X POST http://localhost:8080/llm/query \
-H "Content-Type: application/json" \
-d '{"prompt": "Tell me a joke", "provider": "huggingface"}'
```

#### Payload Options:

| Field     | Type   | Description                                  |
|-----------|--------|----------------------------------------------|
| `prompt`  | string | The user input prompt                        |
| `provider`| string | `openai` or `huggingface`                    |

---

## ✅ Example Responses

**Hugging Face:**

```json
{
  "response": "Why did the chicken cross the road? To get to the other side!"
}
```

**OpenAI:**

```json
{
  "response": "Sure, here's a joke: ..."
}
```

---

## 📄 Project Structure

```
llm-connector-service-java/
├── src/
│   ├── main/java/com/example/llm/
│   │   ├── controller/LLMController.java
│   │   ├── service/LLMService.java
│   │   └── dto/PromptRequest.java
│   └── resources/application.properties
├── pom.xml
└── README.md
```

---

## ⚠️ Common Errors

| Error                                | Cause                                  | Fix |
|-------------------------------------|----------------------------------------|-----|
| `429 Too Many Requests`             | Exceeded OpenAI quota                  | Add billing or wait |
| `403 Forbidden (Hugging Face)`      | Invalid or insufficient token          | Use personal access token with "read" |
| `404 Not Found` (Hugging Face)      | Model not public or no API access      | Use a verified model like `DialoGPT-medium` |
| `timeout` or `connection refused`   | Network issue or model sleeping        | Retry or use OpenAI fallback |

---

## 📘 References

- [OpenAI API Docs](https://platform.openai.com/docs)
- [Hugging Face Inference API](https://huggingface.co/docs/api-inference/index)
