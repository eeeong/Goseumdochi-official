import '../css/academy_modify.css';
import logo from './images/goseumdochi.png';
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Post from "../components/util/post";
import { useLocation } from 'react-router-dom';

function App13() {

    return (
        <div id="App">
            <div id="header_academy_modify"/>
            <div id="body_academy_modify">
                <div id="write_academy_modify">
                    <div id="write_header_academy_modify">
                        <div id="hd">
                            <img src={logo}/>
                            <a>학원 신청서 확인</a>
                        </div>
                    </div>
                    <div id="write1_academy_modify">
                        <a>원장 정보 입력</a>
                        <input
                            type="text"
                            name="directorName"
                            id="name"
                            placeholder="이름"
                            required
                        /><hr/>
                        <input
                            type="text"
                            name="directorPhoneNumber"
                            placeholder="전화번호"
                            required
                        />
                        <input type="submit" value="인증" id="sub1_btn"/><hr/>
                        <input
                            type="text"
                            name="authNumber"
                            placeholder="인증번호"
                            required
                        />
                        <input type="submit" value="확인" id="sub2_btn"/><hr/>
                    </div>
                    <div id="write2_academy_modify">
                        <a>학원 정보 입력</a>
                        <input
                            type="text"
                            name="academyName"
                            placeholder="이름"
                            required
                        /><hr/>
                        <input
                            type="text"
                            name="academyPhoneNumber"
                            placeholder="전화번호"
                            required
                        /><hr/>
                        <input
                            type="text"
                            name="academyPostcode"
                            placeholder="우편주소"
                            id="address_num"
                            readOnly
                            required
                        />
                        <input
                            type="submit"
                            value="검색"
                            id="sub3_btn"
                        /><hr/>
                        <input
                            type="text"
                            name="academyAddress"
                            placeholder="주소"
                            readOnly
                            required
                        /><hr/>
                        <input
                            type="text"
                            name="academyAddressDetail"
                            placeholder="상세주소"
                            required
                        /><hr/>
                    </div>
                    <div id="write_footer_academy_modify">
                        <input type="submit" value="신청서 수정" id="modify_btn"/>
                        <input type="submit" value="취소" id="back_btn"/>
                    </div>
                </div>
            </div>
            <div id="footer_academy_modify">
                <a>문의 | midas2024.ver01@gmail.com</a>
            </div>
        </div>
    );
}

export default App13;