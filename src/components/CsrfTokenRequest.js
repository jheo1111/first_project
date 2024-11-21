import { useEffect, useState } from "react";
import axios from "axios";

const App = () => {
    const [csrfToken, setCsrfToken] = useState("");
    const [loading, setLoading] = useState(true); // 로딩 상태 추가

    useEffect(() => {
        const fetchCsrfToken = async () => {
            try {
                const response = await axios.get("http://localhost:8080/login");
                setCsrfToken(response.data.csrfToken); // CSRF 토큰을 상태에 저장
            } catch (error) {
                console.error("Error fetching CSRF token:", error);
            } finally {
                setLoading(false); // 로딩 상태를 false로 변경
            }
        };

        fetchCsrfToken();
    }, []);

    const handleRequest = async () => {
        if (!csrfToken) {
            console.error("CSRF token is not available");
            return;
        }

        try {
            const response = await axios.post(
                "http://localhost:8080/protected",
                { data: "some data" },
                {
                    headers: {
                        "X-CSRF-TOKEN": csrfToken, // CSRF 토큰을 헤더에 포함
                    },
                }
            );
            console.log("Request success:", response.data);
        } catch (error) {
            console.error("Request failed:", error);
        }
    };

    return (
        <div>
            {loading ? (
                <p>Loading CSRF Token...</p>
            ) : (
                <button onClick={handleRequest}>Send Request</button>
            )}
        </div>
    );
};

export default App;
