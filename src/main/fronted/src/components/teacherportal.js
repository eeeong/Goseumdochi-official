import '../css/teacherportal.css';
import logo from './images/goseumdochi.png';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App26() {
    const [visibleDiv, setVisibleDiv] = useState('Home');
    const [visiblesubDiv, setVisiblesubDiv] = useState('List');
    const [lectureMaterials, setLectureMaterials] = useState([]);
    const [selectedMaterial, setSelectedMaterial] = useState(null);
    const [newMaterial, setNewMaterial] = useState({ title: '', content: '', file: null });
    const [lectureId, setLectureId] = useState(null);  // lectureId 상태 추가

    useEffect(() => {
        if (visibleDiv === 'Lecturedata' && visiblesubDiv === 'List') {
            fetchLectureMaterials();
        }
    }, [visibleDiv, visiblesubDiv]);

    const fetchLectureMaterials = async () => {
        try {
            const response = await axios.get('/api/teacher/lecture-material/list');
            setLectureMaterials(response.data);
        } catch (error) {
            console.error('강의 자료 목록을 불러오는 중 오류가 발생했습니다.', error);
        }
    };

    const handleMaterialClick = async (id) => {
        try {
            const response = await axios.get(`/api/teacher/lecture-material/${id}`);
            setSelectedMaterial(response.data);
            setVisiblesubDiv('View');
        } catch (error) {
            console.error('강의 자료를 불러오는 중 오류가 발생했습니다.', error);
        }
    };

    const handleSave = async () => {
        try {
            const formData = new FormData();
            formData.append('material', new Blob([JSON.stringify(newMaterial)], { type: 'application/json' }));
            formData.append('file', newMaterial.file);

            if (selectedMaterial) {
                await axios.put(`/api/teacher/lecture-material/${selectedMaterial.id}`, formData);
            } else {
                // lectureId가 설정되어 있는지 확인합니다.
                if (lectureId) {
                    await axios.post(`/api/teacher/lecture/${lectureId}/lecture-material/new`, formData);
                } else {
                    console.error('lectureId가 설정되지 않았습니다.');
                }
            }

            setVisiblesubDiv('List');
            fetchLectureMaterials();
        } catch (error) {
            console.error('강의 자료를 저장하는 중 오류가 발생했습니다.', error);
        }
    };

    const handleFileChange = (e) => {
        setNewMaterial({ ...newMaterial, file: e.target.files[0] });
    };

    const showDivHome = () => {
        setVisibleDiv('Home');
    };

    const showDivLecturedata = () => {
        setVisibleDiv('Lecturedata');
        setVisiblesubDiv('List');
    };

    const showsubDivList = () => {
        setVisiblesubDiv('List');
    };

    const showsubDivView= () => {
        setVisiblesubDiv('View');
    };

    const showsubDivWrite = () => {
        setVisiblesubDiv('Write');
    };

    return (
        <div id="App">
            <div id="menu_teacherportal">
                <div id="teacher_info">
                    <img src={logo}/>
                    <h2>고슴도치</h2>
                </div>
                <ul>
                    <li onClick={showDivHome}><a>교과정보</a></li>
                    <li><a>강의관리</a></li>
                    <li onClick={showDivLecturedata}><a>수업자료</a></li>
                    <li><a>과제조회/제출</a></li>
                    <li><a>평가관리</a></li>
                    <li><a>시험관리</a></li>
                    <li><a>과목공지</a></li>
                    <li><a>강의실 나가기</a></li>
                </ul>
            </div>
            <div id="teacherportal_header">
                <div id="menu_btn"/>
                <div id="home_btn"/>
                <div id="title">

                </div>
            </div>
            <div id="contents_teacherportal">
                {visibleDiv === 'Home' && (
                    <>
                        <div id="Home_teacherportal">

                        </div>
                    </>
                )}
                {visibleDiv === 'Lecturedata' && (
                    <>
                        <div id="Lecturedata_teacherportal">
                            <h2>수업자료실</h2>
                            <div id="Lecturedata">
                                {visiblesubDiv === 'List' && (
                                    <div id="List_teacherportal">
                                        <div id="List">
                                            <div id="cate_List">
                                                <div id="no">no</div>
                                                <div id="title">제목</div>
                                                <div id="writer">작성자</div>
                                                <div id="writedate">작성일자</div>
                                            </div>
                                            {lectureMaterials.map((material, index) => (
                                                <div id="body_List" key={material.id}>
                                                    <div id="body_no">{index + 1}</div>
                                                    <div id="body_title"
                                                         onClick={() => handleMaterialClick(material.id)}>{material.title}</div>
                                                    <div id="body_writer">{material.author}</div>
                                                    <div
                                                        id="body_writedate">{new Date(material.createdAt).toLocaleDateString()}</div>
                                                </div>
                                            ))}
                                        </div>
                                        <button id="newRegister" onClick={() => {
                                            setSelectedMaterial(null);
                                            setVisiblesubDiv('Write');
                                        }}>
                                            <span>새로 등록하기</span>
                                        </button>
                                    </div>
                                )}
                                {visiblesubDiv === 'View' && selectedMaterial && (
                                    <div id="View_teacherportal">
                                        <div id="View">
                                            <div id="title_View">{selectedMaterial.title}</div>
                                            <div id="content_View">{selectedMaterial.content}</div>
                                            <div id="file_View">
                                                <a href={selectedMaterial.attachmentPath} target="_blank" rel="noopener noreferrer">첨부파일</a>
                                            </div>
                                        </div>
                                        <button id="back" onClick={() => setVisiblesubDiv('List')}>
                                            <span>뒤로가기</span>
                                        </button>
                                        <button id="revise" onClick={() => setVisiblesubDiv('Write')}>
                                            <span>수정</span>
                                        </button>
                                    </div>
                                )}
                                {visiblesubDiv === 'Write' && (
                                    <div id="Write_teacherportal">
                                        <div id="Write">
                                            <div id="title_Write">
                                                <div id="tWrite">제목</div>
                                                <input type="text" id="titleWrite" value={newMaterial.title} onChange={(e) => setNewMaterial({ ...newMaterial, title: e.target.value })} />
                                            </div>
                                            <div id="content_Write">
                                                <div id="cWrite">내용</div>
                                                <textarea id="contentWrite" value={newMaterial.content} onChange={(e) => setNewMaterial({ ...newMaterial, content: e.target.value })} />
                                            </div>
                                            <div id="file_Write">
                                                <div id="fWrite">첨부파일</div>
                                                <input type="file" id="fileWrite" onChange={handleFileChange} />
                                            </div>
                                        </div>
                                        <button id="save" onClick={handleSave}>
                                            <span>저장</span>
                                        </button>
                                    </div>
                                )}
                            </div>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
}

export default App26;