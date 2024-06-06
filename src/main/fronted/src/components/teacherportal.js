import '../css/teacherportal.css';
import logo from './images/goseumdochi.png';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';

function App26() {
    const location = useLocation();
    const { user, lectureId, id } = location.state || {};

    const [visibleDiv, setVisibleDiv] = useState('Home');
    const [visiblesubDiv, setVisiblesubDiv] = useState('List');
    const [materials, setMaterials] = useState([]);
    const [selectedMaterial, setSelectedMaterial] = useState(null);
    const [newMaterial, setNewMaterial] = useState({ title: '', content: '', file: null });

    useEffect(() => {
        if (lectureId) {
            fetchLectureMaterials();
        } else {
            console.error('No lectureId provided');
        }
    }, [lectureId]);

    const showDivHome = () => {
        setVisibleDiv('Home');
    };

    const showDivAssignment = () => {
        setVisibleDiv('Assignment');
    };

    const showDivLecturedata = () => {
        setVisibleDiv('Lecturedata');
        setVisiblesubDiv('List');
        fetchLectureMaterials();
    };

    const fetchLectureMaterials = async () => {
        try {
            const response = await axios.get(`/api/teacher/lecture/${lectureId}/materials`);
            setMaterials(response.data);
        } catch (error) {
            console.error('Failed to fetch lecture materials:', error);
        }
    };


    const showsubDivList = () => {
        setVisiblesubDiv('List');
    };

    const showsubDivView= () => {
        setVisiblesubDiv('View');
    };

    const showsubDivWrite = (material) => {
        if (material) {
            setNewMaterial({ ...material, file: null });
        } else {
            setNewMaterial({ title: '', content: '', file: null });
        }
        setVisiblesubDiv('Write');
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewMaterial((prev) => ({ ...prev, [name]: value }));
    };

    const handleFileChange = (e) => {
        setNewMaterial((prev) => ({ ...prev, file: e.target.files[0] }));
    };

    const saveMaterial = async () => {
        const formData = new FormData();
        formData.append('material', new Blob([JSON.stringify(newMaterial)], { type: 'application/json' }));
        formData.append('file', newMaterial.file);
        formData.append('id', id);

        try {
            if (newMaterial.id) {
                await axios.put(`/api/teacher/lecture-material/${newMaterial.id}`, formData);
            } else {
                await axios.post(`/api/teacher/lecture/${lectureId}/lecture-material/new`, formData);
            }
            fetchLectureMaterials();
            showsubDivList();
        } catch (error) {
            console.error('Failed to save material:', error);
        }
    };

    const deleteMaterial = async (id) => {
        try {
            await axios.delete(`/api/teacher/lecture-material/${id}`);
            fetchLectureMaterials();
            showsubDivList();
        } catch (error) {
            console.error('Failed to delete material:', error);
        }
    };
    const downloadFile = (url) => {
        window.open(url, '_blank');
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
                    <li onClick={showDivAssignment}><a>과제조회/제출</a></li>
                    <li><a>평가관리</a></li>
                    <li><a>시험관리</a></li>
                    <li><a>과목공지</a></li>
                    <li><a>강의실 나가기</a></li>
                </ul>
            </div>
            <div id="teacherportal_header">
                <div id="menu_btn" />
                <div id="home_btn" />
                <div id="title"></div>
            </div>
            <div id="contents_teacherportal">
                {visibleDiv === 'Home' && (
                    <div id="Home_teacherportal"></div>
                )}
                {visibleDiv === 'Assignment' && (
                    <div id="Assignment_teacherportal"></div>
                )}
                {visibleDiv === 'Lecturedata' && (
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
                                        <div id="rect"/>
                                        <div id="body_List">
                                            {materials.map((material, index) => (
                                                <div key={material.id}>
                                                    <div>{index + 1}</div>
                                                    <div onClick={() => showsubDivView(material)}>{material.title}</div>
                                                    <div>{material.author}</div>
                                                    <div>{new Date(material.createdAt).toLocaleDateString()}</div>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                    <button id="newRegister" onClick={showsubDivWrite}>
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
                                            <a onClick={() => downloadFile(selectedMaterial.attachmentPath)}>파일 다운로드</a>
                                        </div>
                                    </div>
                                    <button id="back" onClick={showsubDivList}>
                                        <span>뒤로가기</span>
                                    </button>
                                    <button id="revise" onClick={() => showsubDivWrite(selectedMaterial)}>
                                        <span>수정</span>
                                    </button>
                                </div>
                            )}
                            {visiblesubDiv === 'Write' && (
                                <div id="Write_teacherportal">
                                    <div id="Write">
                                        <div id="title_Write">
                                            <div id="tWrite">제목</div>
                                            <input type="text" id="titleWrite" name="title" value={newMaterial.title} onChange={handleInputChange} />
                                        </div>
                                        <div id="content_Write">
                                            <div id="cWrite">내용</div>
                                            <input type="text" id="contentWrite" name="content" value={newMaterial.content} onChange={handleInputChange} />
                                        </div>
                                        <div id="file_Write">
                                            <div id="fWrite">첨부파일</div>
                                            <input type="file" id="fileWrite" name="file" onChange={handleFileChange} />
                                        </div>
                                    </div>
                                    <button id="save" onClick={saveMaterial}>
                                        <span>저장</span>
                                    </button>
                                </div>
                            )}
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default App26;