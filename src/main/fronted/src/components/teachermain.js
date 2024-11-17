import '../css/teachermain.css';
import logo from './images/goseumdochi.png';
import {useLocation, useNavigate} from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

function App25() {
    const location = useLocation();
    const navigate = useNavigate();

    const { user } = location.state || {};

    const [lectures, setLectures] = useState([]);
    const [selectedLectureId, setSelectedLectureId] = useState(null);

    const [lectureList, setLectureList] = useState([]);
    const [calendarEvents, setCalendarEvents] = useState([]);
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [selectedEvent, setSelectedEvent] = useState(null); // 선택된 이벤트 상태
    const [isModalOpen, setIsModalOpen] = useState(false); // 모달 상태


    useEffect(() => {
        const fetchLectures = async () => {
            try {
                const response = await axios.get(`/api/teacher/${user.id}/lecture`);
                setLectures(response.data);
            } catch (error) {
                console.error('Failed to fetch lectures:', error);
            }
        };

        const fetchCalendarEvents = async () => {
            try {
                const response = await axios.get(`/api/calendar/events/teacher/${user.id}`);

                const events = Array.isArray(response.data) ? response.data : [];

                const formattedEvents = events.map((event) => ({
                    title: event.title,
                    date: event.date,
                    type: event.eventType === 'assignment' ? '과제' : '시험',
                    content: event.eventType === 'assignment' ? event.content : '시험 설명을 입력하세요',
                    points: event.eventType === 'assignment' ? event.points : null,
                    duration: event.eventType === 'exam' ? event.duration : null
                }));

                setCalendarEvents(formattedEvents);
            } catch (error) {
                console.error('Error fetching calendar events:', error);
            }
        };

        fetchLectures();
        fetchCalendarEvents();
    }, [user.id]);

    const GoLectureManage = () => {
        navigate('/teacher/lecture/manage', { state: { user: user } });
    };

    const GoTeacherPortal = () => {
        if (selectedLectureId) {
            navigate('/teacherportal', { state: { user: user, lectureId: selectedLectureId } });
        } else {
            alert("강의를 선택해주세요.");
        }
    };

    const GoLectureFind = () => {
        navigate('/teacher/lecture/find', { state: { user: user } });
    };

    const GoMessageList = () => {
        navigate('/message/list', { state: { user: user } });
    };

    const GoLecturePotal = (lecture) => {
        navigate('/lectureportal', { state: { user: user, lecture: lecture } });
    };

    const tileContent = ({ date, view }) => {
        if (view === 'month') {
            const eventsOnThisDay = calendarEvents.filter(
                (event) => new Date(event.date).toDateString() === date.toDateString()
            );

            return eventsOnThisDay.map((event, index) => (
                <div
                    key={index}
                    className="event"
                    onClick={() => handleEventClick(event)} // 클릭 시 이벤트 정보 표시
                    style={{ cursor: 'pointer', color: 'blue' }}
                >
                    {event.type}: {event.title}
                </div>
            ));
        }
    };

    const handleEventClick = (event) => {
        setSelectedEvent(event); // 선택된 이벤트 설정
        setIsModalOpen(true); // 모달 열기
    };

    const closeModal = () => {
        setIsModalOpen(false); // 모달 닫기
        setSelectedEvent(null); // 선택된 이벤트 초기화
    };

    // 로그아웃 후 통합로그인
    const GoIntegrateLogin = () => {
        navigate('/integrate/login');
    };

    return (
        <div id="App">
            <div id="teachermain-menu">
                <div id="header_teachermain">
                    <img src={logo} alt="logo" />
                </div>
                <div id="user_info">

                </div>
                <div id="buttons_teachermain">
                    <input type="submit" value="강의관리" id="lecture_btn" onClick={GoLectureManage}/>
                    <input type="submit" value="학생관리" id="studentmanage_btn" onClick={GoLectureFind}/>
                    <input type="submit" value="쪽지" id="mypage_btn" onClick={GoMessageList}/>
                    <div id="rect"/>
                </div>
                <div id="contents_teachermain">
                    <div id="contents1_teachermain">
                            <div id="teachermain_calendar">
                                <h2>캘린더</h2>
                                <div id="content_calendar">
                                    <Calendar
                                        onChange={setSelectedDate}
                                        value={selectedDate}
                                        tileContent={tileContent}
                                        className="custom-calendar"
                                    />
                                </div>
                                </div>

                    </div>
                    <div id="contents2_teachermain">
                        <div id="Teacherinfo_teachermain">
                            <h2>Teacher</h2>
                            <div id="contents_Teacherinfo">
                                <div id="blank_Teacherinfo"/>
                                <div id="content_Teacherinfo">
                                    <img src={logo} alt="logo" />
                                    <div id="Teachername">
                                        {user.name}
                                    </div>
                                    <button id="logout" onClick={GoIntegrateLogin}>
                                        로그아웃
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div id="lectureSchedule_teachermain">
                            <h2>강의 선택</h2>
                            <div id="content_lectureSchedule">
                                <select onChange={(e) => setSelectedLectureId(e.target.value)}>
                                    <option value="">강의를 선택해주세요</option>
                                    {lectures.map((lecture) => (
                                        <option key={lecture.id} value={lecture.id}>{lecture.name}</option>
                                    ))}
                                </select> <br/>
                                <input type="submit" value="Teacher Portal" id="teacherportal_btn" onClick={GoTeacherPortal}/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="footer_teachermain">
                    <a>문의 | midas2024.ver01@gmail.com</a>
                </div>
            </div>


            {/* 모달 부분 */}
            {isModalOpen && selectedEvent && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close" onClick={closeModal}>&times;</span>
                        <h2>{selectedEvent.type}: {selectedEvent.title}</h2>
                        <p>내용: {selectedEvent.content}</p>
                        {selectedEvent.type === '과제' && <p>배점: {selectedEvent.points}</p>}
                        {selectedEvent.type === '시험' && <p>시험 시간: {selectedEvent.duration}분</p>}
                    </div>
                </div>
            )}

        </div>
    );
}

export default App25;