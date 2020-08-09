package org.challenge.resource;

import lombok.AllArgsConstructor;

import com.google.common.base.Optional;

import org.apache.commons.lang3.StringUtils;
import org.challenge.AutotagChallengeApp;
import org.challenge.dao.AutotagChallengeDAO;
import org.challenge.model.TagInfo;
import org.challenge.service.TagService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/autotag")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class AutotagChallengeResource {

    private static Logger log = LoggerFactory.getLogger(AutotagChallengeResource.class);

    private final AutotagChallengeDAO autotagChallengeDAO;
    private final TagService tagService;

    @POST
    @Path("/getTags")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<String> getTags(TagInfo tagInfo) {
        final String url = tagInfo.getUrl();
        if (StringUtils.isEmpty(url)) {
            log.warn("URL can not be empty.");
            return Collections.emptyList();
        }
        // check is it in database already, if yes return
        final TagInfo existingTagInfo = autotagChallengeDAO.getTags(url);
        if (existingTagInfo != null && !StringUtils.isEmpty(existingTagInfo.getTags())) {
            return List.of(StringUtils.split(existingTagInfo.getTags(), ","));
        }

        // if not get tags and save to dababase
        try {
            final String tags = tagService.getTags(url);
            final TagInfo info = TagInfo.builder().url(url).tags(tags).build();
            autotagChallengeDAO.addURLTags(info);
            return List.of(StringUtils.split(tags, ","));
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    @GET
    @Path("/getMessage")
    public String getMessage() {
        return "Message from Dropwizard!";
    }
}
