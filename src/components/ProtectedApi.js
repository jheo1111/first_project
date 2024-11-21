// ProtectedApi.js
import { useEffect, useState } from "react";
import axios from "axios";

const ProtectedApi = () => {
    const [posts, setPosts] = useState([]);
    const token = localStorage.getItem("token");

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const response = await axios.get("http://localhost:8080/posts", {
                    headers: {
                        Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 포함
                    },
                });
                setPosts(response.data);
            } catch (error) {
                console.error("Error fetching posts:", error);
            }
        };

        if (token) {
            fetchPosts();
        }
    }, [token]);

    return (
        <div>
            <h1>Posts</h1>
            {posts.length > 0 ? (
                posts.map((post) => (
                    <div key={post.postId}>
                        <h3>{post.title}</h3>
                        <p>{post.body}</p>
                    </div>
                ))
            ) : (
                <p>No posts available</p>
            )}
        </div>
    );
};

export default ProtectedApi;
