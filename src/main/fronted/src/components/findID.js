import '../css/findID.css';
import React, { useState } from 'react';
import axios from 'axios';

function App8() {
    const Gologin=()=>{
        window.location.href='/login'
    }

    const [inputs, setInputs] = useState({
        studentName: '',
        studentPhoneNumber: ''
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
            const response = await axios.post('/api/student/findStudentId', inputs);
            console.log('Form Submit success:', response.data);
            alert(`아이디는 ${response.data}입니다`);
            window.location.href='/login';
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
            <div id="header_findID"/>
            <form id="write_findID" onSubmit={handleSubmit}>
                <h2>아이디 찾기</h2>
                <input
                    type="text"
                    name="studentName"
                    value={inputs.studentName}
                    onChange={handleChange}
                    placeholder="이름"
                    required
                /><hr/>
                <input
                    type="text"
                    name="studentPhoneNumber"
                    value={inputs.studentPhoneNumber}
                    onChange={handleChange}
                    placeholder="010-0000-0000"
                    id="PhoneNum"
                    required
                />
                <input type="submit" value="찾기" id="findID_btn"/><hr/>
            </form>
            <div id="footer_findID"/>
        </div>
    );
}

export default App8;