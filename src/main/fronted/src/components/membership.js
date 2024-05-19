import '../css/membership.css';
import logo from './images/goseumdochi.png';
import React, { useState } from 'react';
import axios from 'axios';
function App2() {

    const [inputs, setInputs] = useState({
        studentId: '',
        studentPassword: '',
        confirmPassword: '',
        studentName:'',
        studentBirthDate: '',
        studentEmail: '',
        studentPhoneNumber: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setInputs(prevInputs => ({
            ...prevInputs,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await
                axios.post('/api/student/signup', inputs);
            console.log('Form Submit success:', response.data);
            alert("회원가입 성공");
        } catch (error) {
            console.error('Form Submit error:', error);
            alert(`${error.response.data}`);
        }
    };

    return (
        <div id="App">
            <div id="header_membership"/>
            <div id="membership_write">
                <div id="membership_write_header">
                    <img src={logo}/>
                    <a>회원가입</a>
                </div>
                <form id="membership_write_buttons" onSubmit={handleSubmit}>
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
                        onChange={handleChange}
                        placeholder="비밀번호"
                        required
                    /><hr/>
                    <input
                        type="password"
                        name="confirmPassword"
                        value={inputs.confirmPassword}
                        onChange={handleChange}
                        placeholder="비밀번호 확인"
                        required
                    /><hr/>
                    <input
                        type="text"
                        name="studentName"
                        value={inputs.studentName}
                        onChange={handleChange}
                        placeholder="이름"
                        required
                    /><hr/>
                    <input
                        type="date"
                        name="studentBirthDate"
                        value={inputs.studentBirthDate}
                        onChange={handleChange}
                        placeholder="출생연도"
                        required
                    /><hr/>
                    <input
                        type="text"
                        name="studentEmail"
                        value={inputs.studentEmail}
                        onChange={handleChange}
                        placeholder="email"
                        required
                    /><hr/>
                    <input
                        type="text"
                        name="studentPhoneNumber"
                        value={inputs.studentPhoneNumber}
                        onChange={handleChange}
                        placeholder="010-0000-0000"
                        required
                    /><hr/>
                    <input type="submit" value="회원가입" id="submit_btn"/>
                </form>
            </div>
            <div id="footer_membership"/>
        </div>
    );
}

export default App2;