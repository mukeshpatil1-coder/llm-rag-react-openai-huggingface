import faiss
import json
import numpy as np
import time
from sentence_transformers import SentenceTransformer

index = faiss.read_index("sap_faiss.index")
with open("sap_field_metadata.json", "r") as f:
    metadata = json.load(f)

model = SentenceTransformer("all-MiniLM-L6-v2")

def query_sap_definitions(user_query, top_k=3):
    query_embedding = model.encode([user_query]).astype("float32")
    start_time = time.time()
    D, I = index.search(query_embedding, top_k)
    end_time = time.time()
    results = [metadata[i] for i in I[0]]
    print(f"Top {top_k} matches for: '{user_query}' (retrieved in {end_time - start_time:.3f}s):")
    for i, res in enumerate(results):
        print(f"{i+1}. {res['field']} - {res['description']} (type={res['data_type']}, length={res['length']})")
    return results

if __name__ == "__main__":
    user_query = "What’s the data type and length of MARA-MATNR?"
    query_sap_definitions(user_query)
