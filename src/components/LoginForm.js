// LoginForm.js
import { useState } from "react";
import axios from "axios";

const LoginForm = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [token, setToken] = useState(null);

    const handleLogin = async () => {
        try {
            const response = await axios.post("http://localhost:8080/login", {
                username,
                password,
            });
            setToken(response.data.token); // 토큰을 상태에 저장
            localStorage.setItem("token", response.data.token); // 로컬 스토리지에 저장
            alert("로그인 성공!");
        } catch (error) {
            alert("로그인 실패: " + error.response.data.message);
        }
    };

    return (
        <div>
            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>Login</button>
        </div>
    );
};

export default LoginForm;
