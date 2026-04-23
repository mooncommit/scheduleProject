# 일정 관리 시스템 API 명세서

---

## 🍬 API 목록

### 🕶️ User API
| Method | URL | Description | 인증 필요 |
|--------|-----|-------------|--------|
| POST | /users | 유저 생성 | ❌ |
| GET | /users | 전체 유저 조회 | ❌ |
| GET | /users/{id} | 단건 유저 조회 | ❌ |
| PUT | /users/{id} | 유저 수정 | ✅ |
| DELETE | /users/{id} | 유저 삭제 | ✅ |
| POST | /users/login | 로그인 | ❌ |

### 📆 Schedule API
| Method | URL | Description | 인증 필요 |
|--------|-----|-------------|--------|
| POST | /schedules | 일정 생성 | ✅ |
| GET | /schedules | 전체 일정 조회 | ❌ |
| GET | /schedules/{id} | 단건 일정 조회 | ❌ |
| PUT | /schedules/{id} | 일정 수정 | ✅ |
| DELETE | /schedules/{id} | 일정 삭제 | ✅ |

---

## 😎 User API

### 1. 유저 생성
- **Method** : `POST`
- **URL** : `/users`
- **인증** : 불필요

#### Request Body
| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| username | String | ✅ | 유저명 |
| email | String | ✅ | 이메일 |
| password | String | ✅ | 비밀번호 (8글자 이상) |

```json
{
    "username": "문승주",
    "email": "msj@gmail.com",
    "password": "12345678"
}
```

#### Response Body
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 유저 ID |
| username | String | 유저명 |
| email | String | 이메일 |
| createdAt | LocalDateTime | 생성일 |
| modifiedAt | LocalDateTime | 수정일 |

```json
{
    "id": 1,
    "username": "문승주",
    "email": "msj@gmail.com",
    "createdAt": "2026-04-19T05:57:58",
    "modifiedAt": "2026-04-19T05:57:58"
}
```

- **Status Code** : `201 Created`

---

### 2. 전체 유저 조회
- **Method** : `GET`
- **URL** : `/users`
- **인증** : 불필요

#### Response Body
```json
[
    {
        "id": 1,
        "username": "문승주",
        "email": "msj@gmail.com",
        "createdAt": "2026-04-19T05:57:58",
        "modifiedAt": "2026-04-19T05:57:58"
    }
]
```

- **Status Code** : `200 OK`

---

### 3. 단건 유저 조회
- **Method** : `GET`
- **URL** : `/users/{id}`
- **인증** : 불필요

#### Path Variable
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 유저 ID |

#### Response Body
```json
{
    "id": 1,
    "username": "문승주",
    "email": "msj@gmail.com",
    "createdAt": "2026-04-19T05:57:58",
    "modifiedAt": "2026-04-19T05:57:58"
}
```

- **Status Code** : `200 OK`

---

### 4. 유저 수정
- **Method** : `PUT`
- **URL** : `/users/{id}`
- **인증** : 필요 (본인만 수정 가능)

#### Path Variable
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 유저 ID |

#### Request Body
| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| username | String | ✅ | 새 유저명 |
| email | String | ✅ | 새 이메일 |
| password | String | ✅ | 현재 비밀번호 |
| newPassword | String | ✅ | 새 비밀번호 |

```json
{
    "username": "문승주2",
    "email": "msj2@gmail.com",
    "password": "12345678",
    "newPassword": "87654321"
}
```

#### Response Body
```json
{
    "id": 1,
    "username": "문승주2",
    "email": "msj2@gmail.com",
    "createdAt": "2026-04-19T05:57:58",
    "modifiedAt": "2026-04-19T06:00:00"
}
```

- **Status Code** : `200 OK`

---

### 5. 유저 삭제
- **Method** : `DELETE`
- **URL** : `/users/{id}`
- **인증** : 필요 (본인만 삭제 가능)

#### Path Variable
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 유저 ID |

- **Status Code** : `200 OK`

---

### 6. 로그인
- **Method** : `POST`
- **URL** : `/users/login`
- **인증** : 불필요

#### Request Body
| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| email | String | ✅ | 이메일 |
| password | String | ✅ | 비밀번호 |

```json
{
    "email": "msj@gmail.com",
    "password": "12345678"
}
```

#### Response Body
```
로그인 성공
```

- **Status Code** : `200 OK`

---

## 📅 Schedule API

### 1. 일정 생성
- **Method** : `POST`
- **URL** : `/schedules`
- **인증** : 필요

#### Request Body
| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| title | String | ✅ | 일정 제목 |
| content | String | ✅ | 일정 내용 |

```json
{
    "title": "운동하기",
    "content": "헬스장 가기"
}
```

#### Response Body
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 일정 ID |
| title | String | 일정 제목 |
| content | String | 일정 내용 |
| userId | Long | 작성자 ID |
| createdAt | LocalDateTime | 생성일 |
| modifiedAt | LocalDateTime | 수정일 |

```json
{
    "id": 1,
    "title": "운동하기",
    "content": "헬스장 가기",
    "userId": 1,
    "createdAt": "2026-04-19T05:57:58",
    "modifiedAt": "2026-04-19T05:57:58"
}
```

- **Status Code** : `201 Created`

---

### 2. 전체 일정 조회
- **Method** : `GET`
- **URL** : `/schedules`
- **인증** : 불필요

#### Response Body
```json
[
    {
        "id": 1,
        "title": "운동하기",
        "content": "헬스장 가기",
        "userId": 1,
        "createdAt": "2026-04-19T05:57:58",
        "modifiedAt": "2026-04-19T05:57:58"
    }
]
```

- **Status Code** : `200 OK`

---

### 3. 단건 일정 조회
- **Method** : `GET`
- **URL** : `/schedules/{id}`
- **인증** : 불필요

#### Path Variable
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 일정 ID |

#### Response Body
```json
{
    "id": 1,
    "title": "운동하기",
    "content": "헬스장 가기",
    "userId": 1,
    "createdAt": "2026-04-19T05:57:58",
    "modifiedAt": "2026-04-19T05:57:58"
}
```

- **Status Code** : `200 OK`

---

### 4. 일정 수정
- **Method** : `PUT`
- **URL** : `/schedules/{id}`
- **인증** : 필요 (본인 일정만 수정 가능)

#### Path Variable
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 일정 ID |

#### Request Body
| 필드 | 타입 | 필수 | 설명 |
|------|------|------|------|
| title | String | ✅ | 수정할 제목 |
| content | String | ✅ | 수정할 내용 |

```json
{
    "title": "운동하기 (수정)",
    "content": "수영장 가기"
}
```

#### Response Body
```json
{
    "id": 1,
    "title": "운동하기 (수정)",
    "content": "수영장 가기",
    "userId": 1,
    "createdAt": "2026-04-19T05:57:58",
    "modifiedAt": "2026-04-19T06:00:00"
}
```

- **Status Code** : `200 OK`

---

### 5. 일정 삭제
- **Method** : `DELETE`
- **URL** : `/schedules/{id}`
- **인증** : 필요 (본인 일정만 삭제 가능)

#### Path Variable
| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | 일정 ID |

#### Response Body
```
삭제 완료
```

- **Status Code** : `200 OK`