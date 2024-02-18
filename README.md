# FinancialService
금융서비스(보안 키패드 및 휴대폰 인증)
- 보안(셔플) 키패드 구성
    - 셔플 기능으로 View가 그려질 때마다 숫자 배열이 다르게 되도록
    - DataBinding과 BindingAdapter를 사용해서 데이터를 다루는 부분까지 진행
- Material Design 적용
- 휴대폰 인증 One Task UI 구성
- Timer를 활용한 인증번호 입력시간 제어
- 문자 자동 인식 기능

## 구현에 필요한 기술들
- GridLayout
- DataBinding
- BindingAdapter
- 정규 표현식
- TextInputLayout
- CountDownTimer
- SmsRetriever: 문자의 인증번호를 직접 입력하지 않고 문자에서 읽어와서 입력
