import '../css/mypage.css';
import logo from './images/goseumdochi.png';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App12() {
    const Gomain=()=>{
        window.location.href='/main'
    }
    const Gonotice=()=>{
        window.location.href='/notice'
    }

    const Gocommunity=()=>{
        window.location.href='/community'
    }

    const Gomypage=()=>{
        window.location.href='/mypage'
    }

    const [visibleDiv, setVisibleDiv] = useState('Profile');
    const [isEditing, setIsEditing] = useState(false);
    const [userInfo, setUserInfo] = useState({
        studentName: '',
        studentPhoneNumber: '',
        studentBirthDate: '',
        studentEmail: '',
        profilePictureUrl: ''
    });

    const [editInputs, setEditInputs] = useState({
        studentName: '',
        studentPhoneNumber: '',
        studentBirthDate: '',
        studentEmail: ''
    });

    const [profilePicture, setProfilePicture] = useState(null);

    useEffect(() => {
        async function fetchUserInfo() {
            try {
                const response = await axios.get('/api/student/info', {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')}`
                    }
                });
                const data = response.data;
                setUserInfo({
                    studentName: data.studentName,
                    studentPhoneNumber: data.studentPhoneNumber,
                    studentBirthDate: data.studentBirthDate,
                    studentEmail: data.studentEmail,
                    profilePictureUrl: data.profilePictureUrl
                });
            } catch (error) {
                console.error('Error fetching user info:', error);
            }
        }
        fetchUserInfo();
    }, []);


    const showDivProfile = () => {
        setVisibleDiv('Profile');
    };

    const showDivChangePW = () => {
        setVisibleDiv('ChangePW');
    };

    const handleEditChange = (e) => {
        const { name, value } = e.target;
        setEditInputs(prevInputs => ({
            ...prevInputs,
            [name]: value
        }));
    };

    const handleEditClick = () => {
        setEditInputs(userInfo);
        setIsEditing(true);
    };

    const handleProfilePictureChange = (e) => {
        setProfilePicture(e.target.files[0]);
    };

    const handleSaveClick = async () => {
        try {
            // 정보 수정 요청
            await axios.post('/api/student/update', editInputs, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });
            setUserInfo(prevInfo => ({
                ...prevInfo,
                ...editInputs
            }));


            // 프로필 사진 업로드 요청
            if (profilePicture) {
                const formData = new FormData();
                formData.append('profilePicture', profilePicture);
                const response = await axios.post('/api/student/uploadProfilePicture', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'Authorization': `Bearer ${localStorage.getItem('token')}`
                    }
                });
                // 프로필 사진 URL 업데이트
                const updatedProfilePictureUrl = response.data.profilePictureUrl;
                setUserInfo(prevInfo => ({
                    ...prevInfo,
                    profilePictureUrl: updatedProfilePictureUrl
                }));
            }

            setIsEditing(false);
            alert('정보 수정 성공');
        } catch (error) {
            console.error('Error updating user info:', error);
            alert('정보 수정에 실패하였습니다.');
        }
    };

    const [inputs, setInputs] = useState({
        currentPassword:'',
        newPassword:'',
        confirmNewPassword:''
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
        if (!inputs.currentPassword || !inputs.newPassword || !inputs.confirmNewPassword) {
            alert('모든 필드를 입력해 주세요.');
            return;
        }
        console.log("폼 데이터:", inputs);
        try {
            const response = await axios.post('/api/student/changePassword', inputs, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });
            console.log('폼 제출 성공:', response.data);
            alert('비밀번호가 변경되었습니다.');
            window.location.href = '/login';
        } catch (error) {
            console.error('폼 제출 오류:', error);
            if (error.response) {
                console.log("오류 응답 데이터:", error.response.data);
                switch (error.response.data) {
                    case "현재 비밀번호가 일치하지 않습니다.":
                    case "새 비밀번호는 현재 비밀번호와 달라야 합니다.":
                    case "새 비밀번호 확인이 일치하지 않습니다.":
                    case "로그인이 필요합니다.":
                        alert(error.response.data);
                        break;
                    default:
                        alert('오류가 발생했습니다. 나중에 다시 시도해 주세요.');
                }
            } else {
                alert('오류가 발생했습니다. 나중에 다시 시도해 주세요.');
            }
        }
    };

    const handleLogout = async () => {
        try {
            await axios.get('/api/student/logout', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });
            localStorage.removeItem('token'); // 로컬 스토리지에서 토큰 제거
            window.location.href = '/login'; // 로그인 페이지로 이동
        } catch (error) {
            console.error('Error during logout:', error);
            alert('로그아웃 중 오류가 발생했습니다.');
        }
        setVisibleDiv('Logout');
    };

    useEffect(() => {
        const profileButton = document.getElementById('my');
        const changePWButton = document.getElementById('changePW');
        const logoutButton = document.getElementById('logout');

        if (visibleDiv === 'Profile') {
            profileButton.classList.add('selected');
            changePWButton.classList.remove('selected');
            logoutButton.classList.remove('selected');
        } else if (visibleDiv === 'ChangePW') {
            profileButton.classList.remove('selected');
            changePWButton.classList.add('selected');
            logoutButton.classList.remove('selected');
        } else {
            profileButton.classList.remove('selected');
            changePWButton.classList.remove('selected');
            logoutButton.classList.add('selected');
        }
    }, [visibleDiv]);

    return (
        <div id="App">
            <div id="mypage-menu">
                <div id="header_mypage">
                    <img src={logo} onClick={Gomain}/>
                    <div id="user_info"></div>
                </div>
                <div id="buttons_mypage">
                    <input type="submit" value="공지사항" id="notice_btn" onClick={Gonotice}/>
                    <input type="submit" value="커뮤니티" id="community_btn" onClick={Gocommunity}/>
                    <input type="submit" value="마이페이지" id="mypage_btn" onClick={Gomypage}/>
                    <div id="rect"/>
                </div>
                <div id="contents_mypage">
                    <div id="contents1_mypage">
                        <div id="userphoto_mypage">
                            <div id="photo">
                                <div id="header_photo"/>
                                {userInfo.profilePictureUrl ? (
                                    <img src={userInfo.profilePictureUrl} alt="Profile" className="profile-img"/>
                                ) : (
                                    <>등록된
                                        <hr/>
                                        사진이
                                        <hr/>
                                        없습니다</>
                                )}
                            </div>
                            {isEditing && (
                                <>
                                    <input type="file" accept="image/*" onChange={handleProfilePictureChange}/>
                                </>
                            )}
                            <button id="my" onClick={showDivProfile}>
                                <span>내 프로필</span>
                            </button>
                            <hr/>
                            <button id="changePW" onClick={showDivChangePW}>
                                <span>비밀번호 변경</span>
                            </button>
                            <hr/>
                            <button id="logout" onClick={handleLogout}>
                                <span>로그아웃</span>
                            </button>
                        </div>
                        <div id="info_mypage">
                            {visibleDiv === 'Profile' && (
                                <>
                                    <div id="category_info_mypage">
                                        <div id="cate_name">이름</div>
                                        <div id="cate_phonenum">휴대전화</div>
                                        <div id="cate_birthdate">생년월일</div>
                                        <div id="cate_email">이메일</div>
                                        <div id="cate_academy">등록된 학원</div>
                                    </div>
                                    <div id="user_info_mypage">
                                        {isEditing ? (
                                            <>
                                                <input type="text" name="studentName" value={editInputs.studentName}
                                                       onChange={handleEditChange} className="user_info_input"/>
                                                <input type="text" name="studentPhoneNumber"
                                                       value={editInputs.studentPhoneNumber} onChange={handleEditChange}
                                                       className="user_info_input"/>
                                                <input type="date" name="studentBirthDate"
                                                       value={editInputs.studentBirthDate} onChange={handleEditChange}
                                                       className="user_info_input"/>
                                                <input type="email" name="studentEmail" value={editInputs.studentEmail}
                                                       onChange={handleEditChange} className="user_info_input"/>
                                            </>
                                        ) : (
                                            <>
                                                <div id="blank"/>
                                                <div id="user_name">{userInfo.studentName}</div>
                                                <div id="user_phonenum">{userInfo.studentPhoneNumber}</div>
                                                <div id="user_birthdate">{userInfo.studentBirthDate}</div>
                                                <div id="user_email">{userInfo.studentEmail}</div>
                                                <div id="user_academy">컴퓨터 학원, 코딩학원</div>
                                            </>
                                        )}
                                    </div>
                                </>
                            )}

                            {visibleDiv === 'ChangePW' && (
                                <>
                                    <form id="ChangePassWord_mypage" onSubmit={handleSubmit}>
                                        <h2>비밀번호 변경</h2>
                                        <input
                                            type="password"
                                          name="currentPassword"
                                          value={inputs.currentPassword}
                                          placeholder="현재 비밀번호"
                                          onChange={handleChange}
                                          id="currentPassword"
                                          required
                                      />
                                      <hr/>
                                      <input
                                          type="password"
                                          name="newPassword"
                                          value={inputs.newPassword}
                                          placeholder="새 비밀번호"
                                          onChange={handleChange}
                                          id="newPassword"
                                          required
                                      />
                                      <hr/>
                                      <input
                                          type="password"
                                          name="confirmNewPassword"
                                          value={inputs.confirmNewPassword}
                                          placeholder="새 비밀번호 확인"
                                          onChange={handleChange}
                                          id="confirmNewPassword"
                                          required
                                      />
                                      <hr/>
                                      <input type="submit" value="비밀번호 변경"/>
                                  </form>
                              </>
                            )}
                        </div>
                    </div>
                </div>
                <div id="b_mypage">
                    <button id="change_btn" onClick={handleEditClick}>
                        <span>수정</span>
                    </button>
                    <hr/>
                    <button id="save_btn" onClick={handleSaveClick}>
                        <span>저장</span>
                    </button>
                    <hr/>
                </div>
                <div id="footer_mypage">
                    <a>문의 | midas2024.ver01@gmail.com</a>
                </div>
            </div>
        </div>
    );
}

export default App12;