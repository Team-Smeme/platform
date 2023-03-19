# platform
for platform based project,  
serviceLess platform

다양한 서비스에 대해 공통적으로 적용될 수 있는 place 들을 구분하여 플랫폼으로 제공한다.

## How to use
- service token 을 발급받는다.

# Authentication place
### 정의
여러 **Account (계정)** 에 대해 **User (사용자)** 를 매핑해준다.  
- Account N : 1 User


# Posting place
### 정의
**writer (글쓴이)** 가 작성한 **content (글)** 를 다룬다.

#### 제공하는 기능
#### 1. content CRUD
- text only
- 이후 media content 로 확장

#### 2. comment CRUD
2+ depth 댓글 가능 (댓글, 대댓글, 대대댓글,..)
