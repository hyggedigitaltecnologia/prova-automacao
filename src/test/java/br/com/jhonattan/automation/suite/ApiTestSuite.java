package br.com.jhonattan.automation.suite;

import org.junit.jupiter.api.Tag;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("br.com.jhonattan.automation")
@IncludeTags("api")
@Tag("suite-api")
public class ApiTestSuite {
}
