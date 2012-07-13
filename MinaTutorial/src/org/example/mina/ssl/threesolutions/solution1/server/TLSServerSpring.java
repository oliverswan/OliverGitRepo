package org.example.mina.ssl.threesolutions.solution1.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TLSServerSpring {

	private static final Logger logger = LoggerFactory.getLogger(TLSServerSpring.class);
	
    public static void main(String[] args) throws Exception {

        getApplicationContext();
        logger.debug("Listening ...");
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return new ClassPathXmlApplicationContext("com/sundoctor/mina/example1/ssl/server/serverContext.xml");
    }
}
