import '../css/findacademyform.css';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function App17() {

    return (
        <div id="App">
            <div id="header_findacademyform"/>
            <div id="write_findacademyform">
                <div id='buttons_findacademyform'>
                    <a>학원 신청서 찾기</a>
                    <input type="name" placeholder="원장 이름"/><hr/>
                    <input type="phonenumber" placeholder="010-0000-0000"/><hr/>
                    <div id="btn_findacademyform">
                        <input type="submit" value="찾기" id="find_btn"/>
                        <input type="submit" value="취소" id="back_btn"/>
                    </div>
                </div>
            </div>
            <div id="footer_findacademyform"/>
        </div>
    );
}

export default App17;