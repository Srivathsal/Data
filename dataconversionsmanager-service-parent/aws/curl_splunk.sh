curl -X POST \
  https://hec.pilogging.a.intuit.com:8088/services/collector/event \
  -H 'authorization: Splunk CC1ADE66-A9D9-4151-A0B4-421B754B2782' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
  "host": "plnl153519627",
  "index": "pcg_remindersvc_preprod",
  "sourcetype": "log4j_json",
  "source": "appender_test",
  "time": "1513308283.986",
  "event": "2017-12-14 21:24:43,986  [threadId=-main] DEBUG SplunkLogTest  - 8d2b998d-d11f-4752-a82e-f9531fd0d257_Long running logger test 0"
}'