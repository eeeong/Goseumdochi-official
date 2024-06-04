import '../css/login.css';
import logo from './images/goseumdochi.png';
import axios from 'axios';
import React, { useState } from 'react';

function App3() {

    const GoFindID=()=>{
        window.location.href='/findID'
    }
    const GoFindPW=()=>{
        window.location.href='/findPW'
    }

    const [inputs, setInputs] = useState({
        studentId: '',
        studentPassword: ''
    })

    const handleChange = (e) => {
        const { name, value } = e.target;
        setInputs(prevInputs => ({
            ...prevInputs,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("Form data:", inputs);
        try {
            const response = await axios.post('/api/student/login', inputs);
            console.log('Form Submit success:', response.data);
            window.location.href='/main';
        } catch (error) {
            console.error('Form Submit error:', error);
            if (error.response) {
                console.log("Error response data:", error.response.data);
                alert(`${error.response.data}`);
            } else {
                alert('An error occurred. Please try again later.');
            }
        }
    };

    return (
        <div id="App">
            <div id="header_Login"/>
            <div id="login_write">
                <div id="login_write_header">
                    <img src={logo}/>
                    <a>로그인</a>
                </div>
                <form id="login_write_buttons" onSubmit={handleSubmit}>
                    <input
                        type="text"
                        name="studentId"
                        value={inputs.studentId}
                        onChange={handleChange}
                        placeholder="아이디"
                        required
                    /><hr/>
                    <input
                        type="password"
                        name="studentPassword"
                        value={inputs.studentPassword}
                        onChange={handleChange}
                        placeholder="비밀번호"
                        required
                    /><hr/>
                    <div id="letter_Login">
                        <button id='findid' onClick={GoFindID}>아이디 찾기</button>
                        <button id='findpw' onClick={GoFindPW}>비밀번호 찾기</button>
                    </div>
                    <input type="submit" value="Login" id="Login_btn"/>
                </form>
            </div>
            <div id="footer_Login"/>
        </div>
    );
}

export default App3;