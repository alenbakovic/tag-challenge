package org.challenge.test.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;

import org.challenge.dao.AutotagChallengeDAO;
import org.challenge.model.TagInfo;
import org.challenge.resource.AutotagChallengeResource;
import org.challenge.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * Unit test for {@link BookstoreResource}
 */
@ExtendWith(DropwizardExtensionsSupport.class)
public class AutotagChallengeResourceTest {

    private static final AutotagChallengeDAO AUTOTAG_CHALLENGE_DAO = mock(AutotagChallengeDAO.class);
    private static final TagService TAG_SERVICE = mock(TagService.class);

    public static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new AutotagChallengeResource(AUTOTAG_CHALLENGE_DAO, TAG_SERVICE))
            .build();

    @Captor
    private ArgumentCaptor<TagInfo> tagCaptor = ArgumentCaptor.forClass(TagInfo.class);

    private TagInfo tagInfo;
    private TagInfo existingInfo;

    @BeforeEach
    public void setUp() {
        tagInfo = TagInfo.builder()
                .url("https://www.bbc.com/sport/football/53710867")
                .build();
        existingInfo = TagInfo.builder()
                .url("https://www.bbc.com/sport/football/53710867")
                .tags("goals, lewandowski, ronaldo, league, champions, season, two, record, one, four")
                .build();
    }

    @AfterEach
    public void tearDown() {
        reset(AUTOTAG_CHALLENGE_DAO);
    }

    @Test
    public void testExistingTags() throws IOException {
//        doNothing().when(AUTOTAG_CHALLENGE_DAO).addURLTags(any(TagInfo.class));
        when(AUTOTAG_CHALLENGE_DAO.getTags(any(String.class))).thenReturn(existingInfo);

        final Response response = RESOURCES.target("/autotag/getTags")
                .request()
                .post(Entity.entity(tagInfo, MediaType.APPLICATION_JSON_TYPE));

        verify(AUTOTAG_CHALLENGE_DAO, times(0)).addURLTags(any(TagInfo.class));
        verify(TAG_SERVICE, times(0)).getTags(any(String.class));
        assertEquals(response.getStatusInfo(), Response.Status.OK);
    }

    @Test
    public void testNonExistingTags() throws IOException {
        doNothing().when(AUTOTAG_CHALLENGE_DAO).addURLTags(any(TagInfo.class));
        when(AUTOTAG_CHALLENGE_DAO.getTags(any(String.class))).thenReturn(null);
        when(TAG_SERVICE.getTags(any(String.class))).thenReturn(existingInfo.getTags());

        final Response response = RESOURCES.target("/autotag/getTags")
                .request()
                .post(Entity.entity(tagInfo, MediaType.APPLICATION_JSON_TYPE));

        verify(AUTOTAG_CHALLENGE_DAO, times(1)).addURLTags(any(TagInfo.class));
        verify(TAG_SERVICE, times(1)).getTags(any(String.class));
        assertEquals(response.getStatusInfo(), Response.Status.OK);
    }

    @Test
    public void testGetMessage() {
        final String s = RESOURCES.target("/autotag/getMessage").request().get(String.class);
        assertEquals(s, "Message from Dropwizard!");
    }
}
