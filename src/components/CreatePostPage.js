import React, { useState } from 'react';
import { TextField } from '@mui/material';
import { blue, CustomButton } from './CustomButton';
import { red } from '@mui/material/colors';
import { StyledTextarea } from './StyledTextArea';
import { useNavigate } from 'react-router-dom';

const CreatePostPage = () => {
  const navigate = useNavigate();
  const [post, setPost] = useState({
    title: '',
    author: '',
    content: ''
  });

  const submitPost = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/posts`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: post?.title || '',
          content: post?.content || '',
          author: post?.author || ''
        })
      });
      if (!response.ok) {
        throw new Error('Failed to submit post');
      }
      navigate('/');
    } catch (err) {
      console.error(err);
    }
  };

  return (
      <div style={{ padding: '40px' }}>
        <h1>게시글 작성하기</h1>
        <h2>글 제목</h2>
        <TextField
            id="outlined-basic"
            label="제목"
            variant="outlined"
            value={post.title}
            onChange={(event) => setPost(prev => ({ ...prev, title: event.target.value }))}
        />
        <h2>작성자</h2>
        <TextField
            id="outlined-basic"
            label="작성자"
            variant="outlined"
            value={post.author}
            onChange={(event) => setPost(prev => ({ ...prev, author: event.target.value }))}
        />
        <h2>본문</h2>
        <StyledTextarea
            aria-label="minimum height"
            minRows={3}
            placeholder="내용을 입력하세요"
            value={post.content}
            onChange={(event) => setPost(prev => ({ ...prev, content: event.target.value }))}
        />
        <div style={{ marginTop: '20px' }}>
          <CustomButton style={{ backgroundColor: blue[500] }} onClick={submitPost}>작성</CustomButton>
          <CustomButton style={{ backgroundColor: red[500] }} onClick={() => navigate('/')}>취소</CustomButton>
        </div>
      </div>
  );
};

export default CreatePostPage;