package org.feather.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.user.entity.UserBonusPoints;

@Mapper
public interface UserBonusPointsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBonusPoints record);

    int insertSelective(UserBonusPoints record);

    UserBonusPoints selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBonusPoints record);

    int updateByPrimaryKey(UserBonusPoints record);
}