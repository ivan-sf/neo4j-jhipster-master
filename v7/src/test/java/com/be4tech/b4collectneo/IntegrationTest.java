package com.be4tech.b4collectneo;

import com.be4tech.b4collectneo.V7App;
import com.be4tech.b4collectneo.config.AsyncSyncConfiguration;
import com.be4tech.b4collectneo.config.EmbeddedNeo4j;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { V7App.class, AsyncSyncConfiguration.class })
@EmbeddedNeo4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
