import streamlit as st
import time
import requests

# Setup chat history
if "messages" not in st.session_state:
    st.session_state.messages = []
# Custom CSS to fix and center the title
st.markdown("""
    <style>
        .fixed-title {
            position: fixed;
            top: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 100%;
            text-align: center;
            z-index: 9999;
            box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
            font-family: 'Segoe UI', sans-serif;
            background-color: white;
        }

        .fixed-title h1 {
            margin: 0;
            font-size: 30px;
            color: #111;
        }

        .main-content {
            padding-top: 50px;  /* Offset to avoid overlap with fixed title */
        }
       header {visibility: hidden;} 
    </style>
""", unsafe_allow_html=True)

# Inject fixed title
st.markdown('<div class="fixed-title"><h1>Pentaho DataPipeline AI Assistant</h1></div>', unsafe_allow_html=True)

# Add spacing below the fixed title
st.markdown('<div class="main-content">', unsafe_allow_html=True)

if "is_processing" not in st.session_state:
    st.session_state.is_processing = False

# Display previous messages
for msg in st.session_state.messages:
    with st.chat_message(msg["role"]):
        st.markdown(msg["content"])

# User input
prompt = st.chat_input("How can I help you today?", disabled=st.session_state.is_processing)

if prompt:
    if not st.session_state.is_processing:
        st.session_state.messages.append({"role": "user", "content": prompt})
        st.session_state.is_processing = True
        with st.chat_message("user"):
            st.markdown(prompt)
    
    placeholder = st.empty()
    with placeholder.container():
        st.chat_message("assistant").markdown("Thinking...")

    # Call your LangChain agent here
    # For demo: dummy response
    response = requests.post("http://127.0.0.1:5000/predict", json={"prompt": prompt})
    placeholder.empty()
    st.session_state.is_processing = False
    content_type = response.headers.get("Content-Type", "")
    if "application/zip" in content_type:
        zip_bytes = response.content
        assistant_message = f"ZIP file ready: **Download below**"
        st.session_state.messages.append({"role": "assistant", "content": assistant_message, "file_bytes": zip_bytes})
    else:
        data = response.json()
        st.session_state.messages.append({"role": "assistant", "content": response.json().get('response')})
    with st.chat_message("assistant"):
        st.markdown(response)
    st.rerun()