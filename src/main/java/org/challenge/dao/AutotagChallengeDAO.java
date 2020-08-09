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
    @SqlUpdate("INSERT INTO books (title, numberofpages, genre, isbn, authors) " +
            "VALUES (:title, :numberOfPages, :genre, :isbn, :authors)")
    void addBook(@BindBean TagInfo tagInfo);

    @SqlQuery("SELECT * FROM books WHERE isbn = :isbn")
    TagInfo getBookByISBN(@Bind("isbn") String isbn);
}
