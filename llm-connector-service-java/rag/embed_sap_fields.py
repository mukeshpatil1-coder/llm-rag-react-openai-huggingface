import pandas as pd
import numpy as np
import faiss
import json
from sentence_transformers import SentenceTransformer

df = pd.read_csv("sap_field_definitions.csv")
df["text"] = df.apply(lambda row: f"{row['field']}: {row['description']} (type={row['data_type']}, length={row['length']})", axis=1)

model = SentenceTransformer("all-MiniLM-L6-v2")
embeddings = model.encode(df["text"].tolist(), show_progress_bar=True).astype("float32")

with open("sap_field_metadata.json", "w") as f:
    json.dump(df.drop(columns=["text"]).to_dict(orient="records"), f, indent=2)

index = faiss.IndexFlatL2(embeddings.shape[1])
index.add(embeddings)
faiss.write_index(index, "sap_faiss.index")
