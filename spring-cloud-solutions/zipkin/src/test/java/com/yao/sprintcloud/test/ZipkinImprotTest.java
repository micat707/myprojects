package com.yao.sprintcloud.test;

import org.junit.Test;
import org.springframework.cloud.sleuth.metric.NoOpSpanMetricReporter;
import org.springframework.cloud.sleuth.zipkin.HttpZipkinSpanReporter;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import zipkin.Codec;
import zipkin.Span;

public class ZipkinImprotTest {
    @Test
    public void testPostDataToZipkinServer(){
        String url = "http://localhost:8087";
        //可以同时发送多条数据，以下是两条数据
        String data = "{\"traceId\":\"b27d50e739a9241b\",\"id\":\"2641fb830471fe4e\",\"name\":\"http:/saybhello\",\"parentId\":\"b5d9d2f28ca9a6ee\",\"timestamp\":1546652853045000,\"duration\":19000,\"annotations\":[{\"timestamp\":1546652853045000,\"value\":\"cs\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"timestamp\":1546652853064000,\"value\":\"cr\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}}],\"binaryAnnotations\":[{\"key\":\"http.host\",\"value\":\"192.168.1.105\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"key\":\"http.method\",\"value\":\"GET\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"key\":\"http.path\",\"value\":\"/saybhello\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"key\":\"http.url\",\"value\":\"http://192.168.1.105:8085/saybhello?name=ServiceA\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"key\":\"spring.instance_id\",\"value\":\"192.168.1.105:serviceA:8084\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}}]}\n" +
                "{\"traceId\":\"b27d50e739a9241b\",\"id\":\"b5d9d2f28ca9a6ee\",\"name\":\"http:/sayhello\",\"parentId\":\"b27d50e739a9241b\",\"annotations\":[{\"timestamp\":1546652852989000,\"value\":\"sr\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"timestamp\":1546652854469000,\"value\":\"ss\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}}],\"binaryAnnotations\":[{\"key\":\"lc\",\"value\":\"hystrix\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"key\":\"mvc.controller.class\",\"value\":\"ServiceAController\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"key\":\"mvc.controller.method\",\"value\":\"sayhello\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"key\":\"spring.instance_id\",\"value\":\"192.168.1.105:serviceA:8084\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}},{\"key\":\"thread\",\"value\":\"hystrix-serviceb-2\",\"endpoint\":{\"serviceName\":\"servicea\",\"ipv4\":\"192.168.1.105\",\"port\":8084}}]}";

        Span span =  Codec.JSON.readSpan(data.getBytes());
        postDataToZipkin(url, span);
    }

    private void postDataToZipkin(String url, Span data){
        ZipkinSpanReporter reporter = new HttpZipkinSpanReporter(url, 1,
                false, new NoOpSpanMetricReporter());
        reporter.report(data);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
