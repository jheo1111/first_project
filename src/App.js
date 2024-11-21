import React from 'react';
import { Routes, Route } from 'react-router-dom';
import LoginPage from './LoginPage';
import ListPage from './ListPage';
import CreatePostPage from './CreatePostPage';
import PostDetailPage from './PostDetailPage';
import './App.css';

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/" element={<ListPage />} />
                <Route path="/post/create" element={<CreatePostPage />} />
                <Route path="/post/:id" element={<PostDetailPage />} />
            </Routes>
        </div>
    );
}

export default App;