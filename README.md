# Android_채용_과제

- 지원자: 최현지

## 프로젝트 구조

### clean Architecture
 - data
 - domain
 - presentation

각자의 Layer 모듈의 관심사를 분리하여 단방향 흐름이 되도록 노력했습니다.

data Layer에서는 서버와 데이터 베이스와 관련된 작업,

presentation Layer는 UI와 관련된 작업,

domain Layer는 이 둘을 연결하는 역할을 하면서도 *명세 역할을 하는 모듈입니다.

<br/>

### MVVM 
#### view Binding과 Data Binding

- page 단위는 view Binding
- Adapter 등의 item 단위는 Data Binding

data binding의 가장 큰 장점은 데이터를 바로 UI에 매칭하여
binding 로직을 줄일 수 있는 것이라고 생각합니다.

단점이라면, 복잡한 UI 요구사항에서 관리가 어려워질 수도 있다고 생각합니다.

그래서 규모가 크다고 할 수 있는 page 단위는 view binding로,

adapter의 item처럼 비교적 data와 UI가 바로 연결되는 item 단위는 data binding으로 연결하였습니다.

<br/>

### Base ViewModel

- sampleResult

data layout의 네트워크 통신들은 기본적으로 에러가 발생할 수 있습니다.

여러 곳에서 발생할 수 있는 에러는 useCase를 거쳐 viewModel를 통해 최종적으로 UI에서 소비하여 사용자에게 피드백 될 수 있습니다.

해당 에러들을 손쉽게 처리하게 위해 base ViewModel을 만들어 일관성 있게 처리하고자 하였습니다.

현재는 프로젝트에서는 자체적으로 에러처리를 하는 paging3 라이브러리의 call 밖에 존재하지 않아 활용성이 적지만,

현재 구조를 통해 여러 Call에 대한 에러처리를 일관적이게 처리할 수 있게 됩니다.

<br/>

### 검색 결과의 동일한 키워드에 대한 5분 캐싱

- RemoteMediator
- Room

RemoteMediator를 이용하여 검색 결과로 가져온 데이터를 Room에 적재하며, 
보관 기간은 5분으로 설정되어 있습니다.


저장 시점부터 5분이 지난 데이터는 폐기됩니다.

5분이 지나지 않아 폐기되지 않은 데이터는 다음 호출에 사용하여,
네트워크 통신 없이 검색 결과를 보여줍니다.

<br/><br/><br/>


---



