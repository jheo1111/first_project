import React, { useState } from 'react';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  // 로그인 폼 제출 핸들러
  const handleLogin = (event) => {
    event.preventDefault();

    // CSRF 토큰을 meta 태그에서 읽어오기
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

    // 로그인 API 요청 보내기
    fetch('/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': csrfToken, // CSRF 토큰을 헤더에 포함
      },
      body: JSON.stringify({ username, password }), // 사용자 입력 데이터
    })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Login failed');
          }
          return response.json();
        })
        .then((data) => {
          // 로그인 성공 시 처리 (예: 리다이렉트, 성공 메시지 등)
          console.log('Login successful', data);
        })
        .catch((error) => {
          // 로그인 실패 시 처리
          setErrorMessage('Login failed');
          console.error('Error:', error);
        });
  };

  return (
      <div>
        <h2>Login</h2>
        <form onSubmit={handleLogin}>
          <div>
            <label htmlFor="username">Username:</label>
            <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
            />
          </div>
          <div>
            <label htmlFor="password">Password:</label>
            <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />
          </div>
          <button type="submit">Login</button>
          {errorMessage && <p>{errorMessage}</p>}
        </form>
      </div>
  );
}

export default Login;
