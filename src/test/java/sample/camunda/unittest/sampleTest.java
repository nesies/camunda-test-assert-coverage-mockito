package sample.camunda.unittest;

import java.util.Map;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.camunda.community.process_test_coverage.engine.platform7.ProcessCoverageInMemProcessEngineConfiguration;
import org.camunda.community.process_test_coverage.junit5.platform7.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.externalTask;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;


@ExtendWith(ProcessEngineCoverageExtension.class)
public class sampleTest {
    static ProcessEngineCoverageExtension extension = ProcessEngineCoverageExtension.builder().build();
    static public ProcessEngine processEngine; 

    @Test
    @Deployment(resources = {
        "sample/camunda/unittest/camunda/sample.bpmn",
        "sample/camunda/unittest/camunda/sample-with-call.bpmn"
      }
    )
    public void testSampleWithCallSuccess() {
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("Process_sample_with_call");
        assertThat(pi).isActive();
    }

    @Test
    @Deployment(resources = {
        "sample/camunda/unittest/camunda/sample.bpmn"
      }
    )
    public void testSampleSuccess() {
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("Process_sample");
        assertThat(pi).isActive();
        assertThat(pi).isWaitingAt("Activity_task_external");
	complete(externalTask("Activity_task_external"));
	assertThat(pi).isEnded();
  }
}
