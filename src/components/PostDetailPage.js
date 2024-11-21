import React, { useEffect, useState } from 'react';
import { Card, CardContent, TextField, Typography } from '@mui/material';
import { red } from '@mui/material/colors';
import { blue, CustomButton } from './CustomButton';
import { StyledTextarea } from './StyledTextArea';
import { useNavigate, useParams } from 'react-router-dom';

const PostDetailPage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [post, setPost] = useState({});
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState({
    content: '',
    author: ''
  });

  async function fetchData() {
    try {
      const response = await fetch(`http://localhost:8080/api/posts/${id}`);
      if (!response.ok) {
        throw new Error('Failed to fetch post');
      }
      const data = await response.json();
      setPost(data);

      const commentsResponse = await fetch(`http://localhost:8080/api/comments?post_id=${id}`);
      if (!commentsResponse.ok) {
        throw new Error('Failed to fetch comments');
      }
      const commentsData = await commentsResponse.json();
      setComments(commentsData.comments);
    } catch (e) {
      console.error(e);
    }
  }

  useEffect(() => {
    fetchData();
  }, [id]);

  const handlePostChange = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/posts/${post.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: post?.title || '',
          content: post?.content || ''
        })
      });
      if (!response.ok) {
        throw new Error('Failed to update post');
      }
    } catch (err) {
      console.error(err);
    }
  };

  const handleCommentChange = async (id, content) => {
    try {
      const response = await fetch(`http://localhost:8080/api/comments/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ content: content })
      });
      if (!response.ok) {
        throw new Error('Failed to update comment');
      }
    } catch (err) {
      console.error(err);
    }
  };

  const changeComment = (commentId, comment) => {
    const indexToUpdate = comments.findIndex((item) => item.id === commentId);
    const newComments = [...comments];
    if (indexToUpdate !== -1) {
      newComments[indexToUpdate] = { ...newComments[indexToUpdate], content: comment };
      setComments(newComments);
    }
  };

  const submitComment = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/comments`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          author: newComment.author,
          content: newComment.content,
          post_id: post.id
        })
      });
      if (!response.ok) {
        throw new Error('Failed to submit comment');
      }
      const data = await response.json();
      setComments([...comments, data]);
      setNewComment({ content: '', author: '' });
    } catch (err) {
      console.error(err);
    }
  };

  return (
      <div style={{ padding: '40px' }}>
        <h1>게시판 상세</h1>
        <h2>글 제목</h2>
        <TextField
            id="outlined-basic"
            label="제목"
            variant="outlined"
            value={post?.title || ''}
            onChange={(event) => setPost(prev => ({ ...prev, title: event.target.value }))}
        />
        <h2>작성자</h2>
        <TextField
            id="outlined-basic"
            label="작성자"
            variant="outlined"
            value={post?.author || ''}
            disabled
        />
        <h2>본문</h2>
        <StyledTextarea
            aria-label="minimum height"
            minRows={3}
            placeholder="내용을 입력하세요"
            value={post?.content || ''}
            onChange={(event) => setPost(prev => ({ ...prev, content: event.target.value }))}
        />
        <div style={{ marginTop: '20px' }}>
          <CustomButton style={{ backgroundColor: blue[500] }} onClick={handlePostChange}>수정</CustomButton>
          <CustomButton style={{ backgroundColor: red[500] }} onClick={() => navigate('/')}>취소</CustomButton>
        </div>
        <div style={{marginTop: 20}}>
          <Card sx={{ marginBottom: 2 }}>
            <CardContent style={{display: 'flex', flexDirection: 'column'}}>
              <h3>댓글 작성자</h3>
              <TextField
                  variant="outlined"
                  value={newComment.author || ''}
                  onChange={(event) => setNewComment(prev => ({...prev, author: event.target.value}))}
              />
              <h3>댓글 내용</h3>
              <TextField
                  variant="outlined"
                  value={newComment.content || ''}
                  onChange={(event) => setNewComment(prev => ({...prev, content: event.target.value}))}
              />
              <CustomButton style={{ backgroundColor: blue[500], marginTop: 10 }} onClick={submitComment}>댓글 작성</CustomButton>
            </CardContent>
          </Card>
          {comments.length > 0 && (
              comments.map((c) => (
                  <Card key={c.id} sx={{ marginBottom: 2 }}>
                    <CardContent>
                      <TextField
                          variant="outlined"
                          value={c?.content || ''}
                          onChange={(event) => changeComment(c.id, event.target.value)}
                      />
                      <Typography variant="h5" component="div">
                        {c?.author || ''}
                      </Typography>
                      <Typography color="text.secondary">
                        {c?.created_at || ''}
                      </Typography>
                      <CustomButton style={{ backgroundColor: blue[500] }} onClick={() => handleCommentChange(c.id, c.content)}>수정</CustomButton>
                    </CardContent>
                  </Card>
              ))
          )}
        </div>
      </div>
  );
};

export default PostDetailPage;