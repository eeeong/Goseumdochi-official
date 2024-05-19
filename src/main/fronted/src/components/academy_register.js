import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // useHistory 훅 말고 useNavigate!! 임포트합니다.
import Post from "../components/util/post";

function App7() {
    // 입력받은 변수 저장 (Controller로 보낼거임)
    const [inputs, setInputs] = useState({
        academyName: '',
        academyPhoneNumber: '',
        academyPostcode: '',
        academyAddress: '',
        academyAddressDetail: '',
        directorName: '',
        directorPhoneNumber: '',
        directorEmail: '',
        authNumber: ''
    });
    // 페이지 변경 위해
    const navigate = useNavigate();

    // 우편주소 찾기 팝업 띄우기 변수
    const [popup, setPopup] = useState(false);
    const handleComplete = (data) => {
        setPopup(!popup);
    }

    // 입력이 바뀔때마다 inputs 변수값 변경
    const handleChange = (e) => {
        const { name, value } = e.target;
        setInputs(prevInputs => ({
            ...prevInputs,
            [name]: value
        }));
    };

    // 폼 제출하면 실행 함수
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // axios를 사용하여 서버에 신청서 등록 요청을 보냅니다.
            const response = await
                axios.post('/api/academy/form', inputs);
            console.log('Form Submit success:', response.data);
            // 성공 후, 다른 페이지로 리디렉션합니다.
            alert("학원신청서 등록 성공")
            navigate('/');
        } catch (error) {
            console.error('Form Submit error:', error);
            alert(`${error.response.data}`); // 작은따옴표(')가 아니라 왼쪽위(`)
        }
    };

    // 화면 리턴
    return (
        <div>
            <div>
                <h2>학원 등록 신청</h2>
            </div>
            <form onSubmit={handleSubmit}>
                <div>
                    <h5>학원 정보 입력</h5>
                    <label>이름
                        <input
                            type="text"
                            name="academyName"
                            value={inputs.academyName}
                            onChange={handleChange}
                            placeholder="이름"
                            required
                        />
                    </label>
                    <label>전화번호
                        <input
                            type="text"
                            name="academyPhoneNumber"
                            value={inputs.academyPhoneNumber}
                            onChange={handleChange}
                            placeholder="전화번호"
                            required
                        />
                    </label>
                    <label>우편주소
                        <input
                            type="text"
                            name="academyPostcode"
                            value={inputs.academyPostcode}
                            onChange={handleChange}
                            placeholder="우편주소"
                            readOnly
                            required
                        />
                    </label>
                    <button type="button" onClick={handleComplete}>우편번호 찾기</button>
                    {popup && <Post home={inputs} setHome={setInputs}></Post>}
                    <label>주소
                        <input
                            type="text"
                            name="academyAddress"
                            value={inputs.academyAddress}
                            onChange={handleChange}
                            placeholder="주소"
                            readOnly
                            required
                        />
                    </label>
                    <label>상세주소
                        <input
                            type="text"
                            name="academyAddressDetail"
                            value={inputs.academyAddressDetail}
                            onChange={handleChange}
                            placeholder="상세주소"
                            required
                        />
                    </label>
                </div>
                <div>
                    <h5>원장 정보 입력</h5>
                    <label>이름
                        <input
                            type="text"
                            name="directorName"
                            value={inputs.directorName}
                            onChange={handleChange}
                            placeholder="이름"
                            required
                        />
                    </label>
                    <label>전화번호
                        <input
                            type="text"
                            name="directorPhoneNumber"
                            value={inputs.directorPhoneNumber}
                            onChange={handleChange}
                            placeholder="전화번호"
                            required
                        />
                    </label>
                    <label>이메일
                        <input
                            type="email"
                            name="directorEmail"
                            value={inputs.directorEmail}
                            onChange={handleChange}
                            placeholder="user@example.com"
                            required
                        />
                    </label>
                    <label>인증번호
                        <input
                            type="text"
                            name="authNumber"
                            value={inputs.authNumber}
                            onChange={handleChange}
                            placeholder="인증번호"
                            required
                        />
                    </label>
                </div>
                <button type="submit">신청서 제출</button>
                <button type="button" onClick={() => {navigate('/')}}>취소</button>
            </form>
        </div>
    );
}

export default App7;