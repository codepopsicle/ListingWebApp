package com.autoscout.springboot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WebControllerTest extends BaseController{

    @Test
    public void uploadListingFileTest() throws Exception {

        uploadListingFile();

        /* Testing if the number of records insert into the database are correct */
        Assert.assertEquals(300, listingRepository.findAll().size());

    }

    @Test
    public void uploadContactFileTest() throws Exception {

        uploadContactFile();

        /* Testing if the number of records insert into the database are correct */
        Assert.assertEquals(599, contactRepository.findAll().size());

    }
}
