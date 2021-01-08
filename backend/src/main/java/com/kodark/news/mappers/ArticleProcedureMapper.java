package com.kodark.news.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


@Mapper
public interface ArticleProcedureMapper {
	
	@Select(value = "{CALL article_emotion_Info_procedure("
			+ "#{_articleId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"		
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> getEmotionInfo(@Param("_articleId") int _articleId);
	
	@Select(value = "{CALL article_comment_reply_procedure("
			+ "#{_articleId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_commentId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"	
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> getCommentReply(@Param("_articleId") int _articleId,@Param("_commentId") int _commentId);
	
}
