# platform
for platform based project,  
serviceLess platform

다양한 서비스에 대해 공통적으로 적용될 수 있는 place 들을 구분하여 플랫폼으로 제공한다.

## How to use
- service token 을 발급받는다.

# Notification place
특정 target 에 대해 알림을 발송한다.

![스크린샷 2023-03-30 오후 11 32 00](https://user-images.githubusercontent.com/88091704/228869943-14371ddb-5f62-459e-9027-dc95e1dbbc99.png)


### Notification Type
- **Cycle**
  - 주기 알림
  - 특정 요일, 특정 시간에 대한 알림
  - `SelfPush` : 등록된 주기에 맞춰 자동 발송
- **Topic**
  - 토픽 알림
  - 특정 토픽에 의존있는 대상들에대한 알림
    - 글을 작성하면 구독자들에게 일괄 알림 (topic : 글 작성자)
    - 이벤트 참여자들에게 일괄 알림 (topic : 이벤트)
    - 광고 수신 동의한 대상에게 일괄 알림 (topic : 광고)
  - `PassivePush` : api call 에 의한 푸시  
- **Single**
  - 단독 알림
  - 특정 대상에게 단독으로 알림 발송
  - `PassivePush` : api call 에 의한 푸시
  
# Authentication place
여러 **Account (계정)** 에 대해 **User (사용자)** 를 매핑해준다.  
- Account N : 1 User


# Writing place
**writer (글쓴이)** 가 작성한 **writing (글)** 를 다룬다.
- [설계](https://github.com/Team-Smeme/platform/wiki/Writing#%EC%84%A4%EA%B3%84)

#### 제공하는 기능
- [simpel CRUD](https://github.com/Team-Smeme/platform/wiki/Writing#%EA%B8%B0%EB%8A%A5)
- [comment C](https://github.com/Team-Smeme/platform/wiki/Writing#depth-crud)
  - 2+ depth 글 기능 (댓글, 대댓글, 대대댓글, ... )
