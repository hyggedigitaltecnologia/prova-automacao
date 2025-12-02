package br.com.jhonattan.automation.suite;

import br.com.jhonattan.automation.web.tests.RegisterFlowTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        RegisterFlowTests.class
})
public class WebTestSuite {
}
