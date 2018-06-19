package com.sd.gmd.config;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;


public class MyJdbcTemplate extends JdbcTemplate {






    @Override
    @Nullable
    public <T> T queryForObject(String sql, @Nullable Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        List<T> results = (List)this.query((String)sql, (Object[])args, (ResultSetExtractor)(new RowMapperResultSetExtractor(rowMapper, 1)));
        return nullableSingleResult(results);
    }

    @Nullable
    public static <T> T nullableSingleResult(@Nullable Collection<T> results) throws IncorrectResultSizeDataAccessException {
        if (CollectionUtils.isEmpty(results)) {
            return null;
        } else if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        } else {
            return results.iterator().next();
        }
    }
}
