# accompanyi

accompany + API = accompanyi

## TL;DR

- CloudWatch에서 제공하는 인스턴스 시스템 정보를 API로 제공
- Grafana Simple Json 플러그인을 활용한 Grafana 그래프 표현
- metricsgraphics 차트 라이브러리를 활용하여 간단한 HTML 차트 작성

## 사용방법

- [지원메트릭](https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/viewing_metrics_with_cloudwatch.html#ec2-cloudwatch-dimensions)

- Grafana연동  API서버 URL(로컬환경에 구축)

  ```
  //DataSource URL
  http://218.236.84.38:8080/grafana
  
  //Dashboard URL
  http://218.236.84.38:3000/d/XnT4-M7iz/new-dashboard-copy?orgId=1
  ```

- GET방식 호출방법

  ```
  http://218.236.84.38:8080/api/awsCloudWatch/CPUUtilization?startTime=2018-05-09 00:55:05&endTime=2018-05-0923:55:05
  ```

- 별도 HTML차트 확인

  ```
  ChartTest.html 실행
  ```

  