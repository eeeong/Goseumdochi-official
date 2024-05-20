import '../css/academy_register.css';
import logo from './images/goseumdochi.png';
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Post from "../components/util/post";
import { useLocation } from 'react-router-dom';

function App7() {
    const location = useLocation();
    const inputDirectorName = location.state?.inputDirectorName || '이름';
    const inputsDirectorPhoneNumber = location.state?.inputsDirectorPhoneNumber || '010-0000-0000';

    const [inputs, setInputs] = useState({
        academyName: '',
        academyPhoneNumber: '',
        academyPostcode: '',
        academyAddress: '',
        academyAddressDetail: '',
        directorName: '',
        directorPhoneNumber: '',
        authNumber: '',
        directorEmail: ''
    });

    const navigate = useNavigate();

    const [popup, setPopup] = useState(false);
    const handleComplete = (data) => {
        setPopup(!popup);
    }

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
                axios.post('/api/academy/form', inputs);
            console.log('Form Submit success:', response.data);
            alert("학원신청서 등록 성공")
            navigate('/');
        } catch (error) {
            console.error('Form Submit error:', error);
            alert(`${error.response.data}`);
        }
    };


    return (
        <div id="App">
            <div id="header_academy_register"/>
            <div id="body_academy_register">
                <form id="write_academy_register" onSubmit={handleSubmit}>
                    <div id="write_header_academy_register">
                        <div id="hd">
                            <img src={logo}/>
                            <a>학원 등록 신청</a>
                        </div>
                    </div>
                    <div id="write1_academy_register">
                        <a>원장 정보 입력</a>
                        <input
                            type="text"
                            name="directorName"
                            value={inputs.directorName} // inputDirectorName
                            onChange={handleChange}
                            placeholder={inputDirectorName}
                            id="name"
                            required
                        /><hr/>
                        <input
                            type="text"
                            name="directorPhoneNumber"
                            value={inputs.directorPhoneNumber} // inputsDirectorPhoneNumber
                            onChange={handleChange}
                            placeholder={inputsDirectorPhoneNumber}
                            required
                        />
                        <input type="submit" value="인증" id="sub1_btn"/><hr/>
                        <input
                            type="text"
                            name="authNumber"
                            value={inputs.authNumber}
                            onChange={handleChange}
                            placeholder="인증번호"
                            required
                        />
                        <input type="submit" value="확인" id="sub2_btn"/><hr/>
                        <input
                            type="text"
                            name="directorEmail"
                            value={inputs.directorEmail}
                            onChange={handleChange}
                            placeholder="이메일"
                            id="email"
                            required
                        /><hr/>
                    </div>
                    <div id="write2_academy_register">
                        <a>학원 정보 입력</a>
                        <input
                            type="text"
                            name="academyName"
                            value={inputs.academyName}
                            onChange={handleChange}
                            placeholder="이름"
                            required
                        /><hr/>
                        <input
                            type="text"
                            name="academyPhoneNumber"
                            value={inputs.academyPhoneNumber}
                            onChange={handleChange}
                            placeholder="전화번호"
                            required
                        /><hr/>
                        <input
                            type="text"
                            name="academyPostcode"
                            value={inputs.academyPostcode}
                            onChange={handleChange}
                            placeholder="우편주소"
                            id="address_num"
                            readOnly
                            required
                        />
                        <input
                            type="submit"
                            value="검색"
                            onClick={handleComplete}
                            id="sub3_btn"
                        />{popup && <Post home={inputs} setHome={setInputs}></Post>}<hr/>
                        <input
                            type="text"
                            name="academyAddress"
                            value={inputs.academyAddress}
                            onChange={handleChange}
                            placeholder="주소"
                            readOnly
                            required
                        /><hr/>
                        <input
                            type="text"
                            name="academyAddressDetail"
                            value={inputs.academyAddressDetail}
                            onChange={handleChange}
                            placeholder="상세주소"
                            required
                        /><hr/>
                    </div>
                    <div id="write_footer_academy_register">
                        <input type="submit" value="신청서 제출" id="submit_btn"/>
                    </div>
                </form>
            </div>
            <div id="footer_academy_register">
                <a>문의 | midas2024.ver01@gmail.com</a>
            </div>
        </div>
    );
}

export default App7;