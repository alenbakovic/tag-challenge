package org.challenge.dao;

import org.challenge.dao.mappers.TagInfoMapper;
import org.challenge.model.TagInfo;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TagInfoMapper.class)
public interface AutotagChallengeDAO {
    @SqlUpdate("INSERT INTO tagged_links (url, tags) VALUES (:url, :tags)")
    void addURLTags(@BindBean TagInfo tagInfo);

    @SqlQuery("SELECT * FROM tagged_links WHERE url = :url")
    TagInfo getTags(@Bind("url") String url);
}
