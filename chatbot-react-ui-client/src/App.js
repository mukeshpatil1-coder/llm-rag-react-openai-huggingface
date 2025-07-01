import React, { useState } from 'react';

function App() {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');

  const sendMessage = async (e) => {
    e.preventDefault();
    const newMessages = [...messages, { sender: 'You', text: input }];
    setMessages(newMessages);
    setInput('');
    
    try {
      const response = await fetch('http://localhost:8080/llm/query', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ prompt: input, provider: 'openai' }) // use 'huggingface' for huggingface LLM
      });

      const responseText = await response.text();

      if (!response.ok) {
        // Try to parse structured error message
        let errorMsg;
        try {
          const errJson = JSON.parse(responseText);
          errorMsg = errJson.error || responseText;
        } catch {
          errorMsg = responseText;
        }
        throw new Error(`Backend Error: ${errorMsg}`);
      }

      const { _response, error } = JSON.parse(responseText);
      setMessages((prev) => [...prev, { sender: 'LLM', text: error || _response }]);
    } catch (err) {
      console.error("Frontend fetch error:", err);
      setMessages((prev) => [...prev, { sender: 'LLM', text: `Error calling backend: ${err.message}` }]);
    }

  };

  return (
    <div style={{ maxWidth: '600px', margin: '2em auto', fontFamily: 'Arial, sans-serif' }}>
      <h2>Java LLM Chatbot</h2>
      <div style={{ background: '#f9f9f9', padding: '1em', borderRadius: '6px', height: '300px', overflowY: 'auto' }}>
        {messages.map((msg, i) => (
          <div key={i} style={{ margin: '0.5em 0' }}>
            <strong style={{ color: msg.sender === 'You' ? 'blue' : 'green' }}>{msg.sender}:</strong> {msg.text}
          </div>
        ))}
      </div>
      <form onSubmit={sendMessage} style={{ marginTop: '1em' }}>
        <input
          value={input}
          onChange={(e) => setInput(e.target.value)}
          style={{ width: '80%' }}
          placeholder="Type a question..."
        />
        <button type="submit">Send</button>
      </form>
    </div>
  );
}

export default App;
