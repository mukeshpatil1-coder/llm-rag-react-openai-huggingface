
# 🤖 LLM Connector Service + RAG + React UI

## 📘 Overview

This project includes a full-stack Retrieval-Augmented Generation (RAG) pipeline with:

- ✅ Java Spring Boot backend (`llm-connector-service-java`)
- ✅ FAISS vector-based retrieval using SAP field definitions (`rag/`)
- ✅ React Chatbot UI (`chatbot-react-ui-client`)

---

## 📘 Working UI
![Uploading Screenshot 2025-07-02 at 12.01.34 AM.png…]()

---

## 📁 Project Structure

```
llm-rag-react-openai-huggingface/
├── llm-connector-service-java/    # Spring Boot backend
│   ├── rag/                       # SAP RAG pipeline (FAISS)
│   ├── src/                       # Controller, Service, Config
│   └── pom.xml
├── chatbot-react-ui-client/             # React frontend
│   └── src/
└── sap_field_definitions.csv     # SAP metadata (for RAG)
```

---

## 🔧 Requirements

- Java 17+
- Python 3.8+
- Node.js + npm
- Maven (`mvn`)
- Python packages:
  ```bash
  pip install pandas sentence-transformers faiss-cpu
  ```

---

### Clone the repo

```bash
git clone https://github.com/mukeshpatil1-coder/llm-rag-react-openai-huggingface.git
cd llm-rag-react-openai-huggingface
```

---

### Configure your API keys

Edit the `src/main/resources/application.properties` file:

```properties
# === OpenAI ===
openai.api.key=sk-<your-openai-api-key>
openai.model=gpt-3.5-turbo

# === Hugging Face ===
huggingface.api.key=hf_<your-hf-token>
huggingface.model.url=https://api-inference.huggingface.co/models/microsoft/DialoGPT-medium
```
---

## 🧠 Step 1: SAP Data Embedding (RAG)

Run the embedding pipeline to index SAP metadata into FAISS:

From root:

```bash
cd llm-connector-service-java/rag
python3 embed_sap_fields.py
```

✔ This generates: `sap_vectors.index` and `sap_fields.pkl`

---

## 🚀 Step 2: Start the Backend

From root:

```bash
cd llm-connector-service-java
mvn spring-boot:run
```

✅ Your Spring Boot app will run on `http://localhost:8080`

---

## 💬 Step 3: Launch React Chatbot UI

From root:

```bash
cd chatbot-react-ui-client
npm install
npm start
```

Open: [http://localhost:3000](http://localhost:3000)

---

## 🧪 Sample Query (via cURL)

```bash
curl -X POST http://localhost:8080/llm/query \
  -H "Content-Type: application/json" \
  -d '{"prompt":"What’s the data type and length of MARA-MATNR?", "provider": "openai"}'
```

```bash
curl -X POST http://localhost:8080/llm/query \
  -H "Content-Type: application/json" \
  -d '{"prompt":"What’s the data type and length of MARA-MATNR?", "provider": "huggingface"}'
```

---

## 🔑 API Key Setup

Create a `.env` file or export directly:

```bash
export OPENAI_API_KEY=your-openai-key
export HUGGINGFACE_API_KEY=your-huggingface-key
```

---

## 🧪 CLI Benchmark (Optional)

```bash
cd llm-connector-service-java/rag
python3 benchmark_retrieval.py
```

---

## 🧩 Features

- RAG augmentation from SAP definitions
- HuggingFace/OpenAI backend support
- Java Spring Boot API
- React frontend chatbot
- FAISS-based top-k semantic retrieval

---

## 🧹 Cleanup

```bash
rm sap_vectors.index sap_fields.pkl
```
