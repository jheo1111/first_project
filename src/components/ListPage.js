import React, { useEffect, useState } from 'react';
import { blue, CustomButton } from './CustomButton';
import { useNavigate } from 'react-router-dom';
import { red } from '@mui/material/colors';
import { Table, TableBody, TableCell, TableHead, TableRow, TextField } from '@mui/material';

const ListPage = () => {
  const navigate = useNavigate();
  const [keyword, setKeyword] = useState('');
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await fetch('http://localhost:8080/api/posts');
        if (!response.ok) {
          throw new Error('Failed to fetch posts');
        }
        const data = await response.json();
        setPosts(data.posts);
      } catch (err) {
        console.error(err);
      }
    }
    fetchData();
  }, []);

  const logoutHandler = async () => {
    const email = localStorage.getItem('email');
    if (!email) {
      navigate('/login');
      return;
    }
    try {
      const response = await fetch(`http://localhost:8080/api/logout`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email })
      });
      if (!response.ok) {
        throw new Error('Logout failed');
      }
      localStorage.removeItem('email');
      navigate('/login');
    } catch (error) {
      console.error(error);
    }
  };

  const searchHandler = async (email) => {
    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) return;
    try {
      const response = await fetch(`http://localhost:8080/api/posts/search?author_email=${email}`);
      if (!response.ok) {
        throw new Error('Search failed');
      }
      const data = await response.json();
      if (data.posts) {
        setPosts(data.posts);
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
      <div style={{ padding: '40px' }}>
        <h1>게시판 리스트</h1>
        <div>
          <TextField
              id="standard-required"
              label="작성자 이메일 검색"
              variant="standard"
              value={keyword}
              onChange={(event) => setKeyword(event.target.value)}
          />
          <CustomButton
              style={{ backgroundColor: blue[500] }}
              onClick={() => searchHandler(keyword)}
          >
            검색
          </CustomButton>
        </div>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>이름</TableCell>
              <TableCell>게시물내용</TableCell>
              <TableCell>작성자</TableCell>
              <TableCell>작성일시</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {posts.map(post => (
                <TableRow
                    key={post.id}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    onClick={() => {
                      navigate(`/post/${post.id}`);
                    }}
                >
                  <TableCell component="th" scope="row">
                    {post.title}
                  </TableCell>
                  <TableCell>{post.content}</TableCell>
                  <TableCell>{post.author}</TableCell>
                  <TableCell>{post.created_at}</TableCell>
                </TableRow>
            ))}
          </TableBody>
        </Table>
        <CustomButton
            style={{ backgroundColor: blue[500] }}
            onClick={() => navigate('/post/create')}
        >
          게시글 작성
        </CustomButton>
        <CustomButton
            style={{ backgroundColor: red[500] }}
            onClick={logoutHandler}
        >
          로그아웃
        </CustomButton>
      </div>
  );
};

export default ListPage;