// src/App.js
import React from "react";
import CsrfTokenRequest from "./components/CsrfTokenRequest";

function App() {
    return (
        <div className="App">
            <h1>CSRF Token Request Example</h1>
            <CsrfTokenRequest />
        </div>
    );
}

export default App;
