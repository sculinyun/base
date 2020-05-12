package damowang.base.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseMybatisMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
