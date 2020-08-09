package org.challenge.test.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.challenge.AutotagChallengeApp;
import org.challenge.model.TagInfo;
import org.challenge.resource.AutotagChallengeResource;
import org.challenge.service.TagService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * Unit test for {@link BookstoreResource}
 */
@ExtendWith(DropwizardExtensionsSupport.class)
public class AutotagChallengeResourceTest {

//    private static final BookDAO BOOK_DAO = mock(BookDAO.class);
    private static final TagService tagService = new TagService();

    public static final ResourceExtension RESOURCES = ResourceExtension.builder()
//            .addResource(new AutotagChallengeResource(BOOK_DAO))
            .addResource(new AutotagChallengeResource(tagService))
            .build();

    @Captor
    private ArgumentCaptor<TagInfo> tagCaptor = ArgumentCaptor.forClass(TagInfo.class);

    private TagInfo tagInfo;

    @BeforeEach
    public void setUp() {
        tagInfo = TagInfo.builder()
//                .title("The Bridge on the Drina - Na Drini cuprija")
//                .authors("Ivo Andric")
//                .numberOfPages(379)
//                .genre("Historical fiction")
//                .isbn("9788650526507")
                .build();
    }

    @AfterEach
    public void tearDown() {
//        reset(BOOK_DAO);
    }

    @Test
    public void addBook() {
//        doNothing().when(BOOK_DAO).addBook(any(Book.class));
//
//        final Response response = RESOURCES.target("/bookstore/addBook")
//                .request()
//                .post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));
//
//        verify(BOOK_DAO).addBook(bookCaptor.capture());
//        assertEquals(bookCaptor.getValue().getIsbn(), book.getIsbn());
//        assertEquals(response.getStatusInfo(), Response.Status.OK);
    }

    @Test
    public void getBookByISBN() {
//        when(BOOK_DAO.getBookByISBN(anyString())).thenReturn(book);
//
//        Book responseBook = RESOURCES.target("/bookstore/getBook")
//                .queryParam("isbn", "9788650526507")
//                .request().get(Book.class);
//
//        verify(BOOK_DAO).getBookByISBN(anyString());
//        assertEquals(responseBook.getIsbn(), book.getIsbn());
    }

    @Test
    public void testGetMessage() {
        final String s = RESOURCES.target("/autotag/getMessage").request().get(String.class);
        assertEquals(s, "Message from Dropwizard!");
    }
}
