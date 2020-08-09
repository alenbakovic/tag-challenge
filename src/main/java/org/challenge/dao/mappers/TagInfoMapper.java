package org.challenge.dao.mappers;

import org.challenge.model.TagInfo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TagInfoMapper implements ResultSetMapper<TagInfo> {
    @Override
    public TagInfo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        return TagInfo.builder()
                .url(resultSet.getString("url"))
                .tags(resultSet.getString("tags"))
                .build();
    }
}
