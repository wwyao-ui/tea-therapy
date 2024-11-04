package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Userbrowsinghistory;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author he
 * @since 2024-07-11
 */
@Component
public interface UserbrowsinghistoryMapper extends BaseMapper<Userbrowsinghistory> {
    @Insert("INSERT INTO userbrowsinghistory(uid, tid) VALUES(#{uid}, #{tid})")
    int insertBrowsingRecord(Userbrowsinghistory  browsinghistory);
}
