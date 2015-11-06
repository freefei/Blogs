package com.renfei.blog.common.mysql.dao;

import com.google.common.collect.Maps;
import com.renfei.blog.common.json.JsonMapper;
import com.renfei.blog.common.utils.Constants;
import com.renfei.blog.common.utils.Paging;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.renfei.blog.common.utils.Arguments.isEmpty;

/**
 * 对应的*Mapper文件的NameSpace应该为简单类名, 具体详情可见编码规范:
 *
 * MyBatis Base Dao
 * CREATED BY: IntelliJ IDEA
 * AUTHOR: haolin
 * ON: 14-7-12
 */
@SuppressWarnings("all")
public abstract class MyBatisDao<T> extends SqlSessionDaoSupport {

    protected static final String CREATE = "create";	    //添加
    protected static final String CREATES = "creates";	    //批量添加
    protected static final String DELETE = "delete";	    //删除
    protected static final String DELETES = "deletes";	    //批量删除
    protected static final String UPDATE = "update";	    //更新
    protected static final String LOAD = "load";		    //单个主键查询对象
    protected static final String LOADS = "loads";		    //主键列表查询对象列表
    protected static final String LIST = "list";		    //列表条件查询
    protected static final String COUNT = "count";		    //计数
    protected static final String PAGING = "paging";	    //分页查询

    /**
     * Namespace should be simple className
     */
    public final String nameSpace;


    public MyBatisDao(){
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            nameSpace = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        } else {
            //解决cglib实现aop时转换异常
            nameSpace = ((Class<T>) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        }
    }

    /**
     * 添加对象
     * @param t 范型对象
     * @return 增加记录数
     */
    public Boolean create(T t){
        return getSqlSession().insert(sqlId(CREATE), t) == 1;
    }

    /**
     * 批量添加对象
     * @param ts 范型对象
     * @return 增加记录数
     */
    public Integer creates(List<T> ts){
        return getSqlSession().insert(sqlId(CREATES), ts);
    }

    /**
     * 批量添加对象
     * @param t0 第一个范型对象
     * @param t1 第一个范型对象
     * @param tn 第N个范型对象
     * @return 增加记录数
     */
    public Integer creates(T t0, T t1, T... tn){
        return getSqlSession().insert(sqlId(CREATES), Arrays.asList(t0, t1, tn));
    }

    /**
     * 删除
     * @param id 主键
     * @return 删除记录数
     */
    public Boolean delete(Long id){
        return getSqlSession().delete(sqlId(DELETE), id) == 1;
    }

    /**
     * 批量删除
     * @param ids 主键列表
     * @return 删除记录数
     */
    public Integer deletes(List<Long> ids){
        return getSqlSession().delete(sqlId(DELETES), ids);
    }

    /**
     * 批量删除
     * @param id0 第一个id
     * @param id1 第二个id
     * @param idn 第N个id
     * @return 删除记录数
     */
    public Integer deletes(Long id0, Long id1, Long... idn){
        return getSqlSession().delete(sqlId(DELETES), Arrays.asList(id0, id1, idn));
    }

    /**
     * 更新对象
     * @param t 范型对象
     * @return 更新记录数
     */
    public Boolean update(T t){
        return getSqlSession().update(sqlId(UPDATE), t) == 1;
    }

    /**
     * 查询单个对象
     * @param id 主键
     * @return 对象
     */
    public T load(Integer id){
        return load(Long.valueOf(id));
    }

    /**
     * 查询单个对象
     * @param id 主键
     * @return 对象
     */
    public T load(Long id){
        return getSqlSession().selectOne(sqlId(LOAD), id);
    }

    /**
     * 批量删除
     * @param ids 主键列表
     * @return 删除记录数
     */
    public List<T> loads(List<Long> ids){
        if (isEmpty(ids)) {
            return Collections.emptyList();
        }
        return getSqlSession().selectList(sqlId(LOADS), ids);
    }

    /**
     * 查询对象列表
     * @param id0 第一个id
     * @param id1 第二个id
     * @param idn 第N个id
     * @return 对象列表
     */
    public List<T> loads(Long id0, Long id1, Long... idn){
        return getSqlSession().selectList(sqlId(LOADS), Arrays.asList(id0, id1, idn));
    }

    /**
     * 查询所有对象列表
     * @return 所有对象列表
     */
    public List<T> listAll(){
        return list((T)null);
    }

    /**
     * 查询对象列表
     * @param t 范型对象
     * @return 查询到的对象列表
     */
    public List<T> list(T t){
        return getSqlSession().selectList(sqlId(LIST), t);
    }

    /**
     * 查询对象列表
     * @param criteria Map查询条件
     * @return 查询到的对象列表
     */
    public List<T> list(Map<?, ?> criteria){
        return getSqlSession().selectList(sqlId(LIST), criteria);
    }

    /**
     * 查询分页对象
     * @param offset 起始偏移
     * @param limit 分页大小
     * @return 查询到的分页对象
     */
    public Paging<T> paging(Integer offset, Integer limit){
        return paging(offset, limit, Collections.EMPTY_MAP);
    }

    /**
     * 查询分页对象
     * @param offset 起始偏移
     * @param limit 分页大小
     * @param criteria 范型对象, 即查询条件
     * @return 查询到的分页对象
     */
    @SuppressWarnings("unchecked")
    public Paging<T> paging(Integer offset, Integer limit, T criteria){
        Map<String, Object> params = Maps.newHashMap();
        if (criteria != null) {    //查询条件不为空
            Map<String, Object> objMap = JsonMapper.nonEmptyMapper().getMapper().convertValue(criteria, Map.class);
            params.putAll(objMap);
        }
        // get total count
        Long total = getSqlSession().selectOne(sqlId(COUNT), criteria);
        if (total <= 0){
            return new Paging<T>(0L, Collections.<T>emptyList());
        }
        params.put(Constants.VAR_OFFSET, offset);
        params.put(Constants.VAR_LIMIT, limit);
        // get data
        List<T> datas = getSqlSession().selectList(sqlId(PAGING), params);
        return new Paging<T>(total, datas);
    }

    /**
     * 查询分页对象
     * @param offset 起始偏移
     * @param limit 分页大小
     * @param criteria Map查询条件
     * @return 查询到的分页对象
     */
    public Paging<T> paging(Integer offset, Integer limit, Map<String, Object> criteria) {
        if (criteria == null) {    //如果查询条件为空
            criteria = Maps.newHashMap();
        }
        // get total count
        Long total = getSqlSession().selectOne(sqlId(COUNT), criteria);
        if (total <= 0){
            return new Paging<T>(0L, Collections.<T>emptyList());
        }
        criteria.put(Constants.VAR_OFFSET, offset);
        criteria.put(Constants.VAR_LIMIT, limit);
        // get data
        List<T> datas = getSqlSession().selectList(sqlId(PAGING), criteria);
        return new Paging<T>(total, datas);
    }

    /**
     * 分页查询，offset， limit都丢在map里面
     * @param criteria 所有查询参数
     * @return 查询到的分页对象
     */
    public Paging<T> paging(Map<String, Object> criteria) {
        if (criteria == null) {    //如果查询条件为空
            criteria = Maps.newHashMap();
        }
        // get total count
        Long total = getSqlSession().selectOne(sqlId(COUNT), criteria);
        if (total <= 0){
            return new Paging<T>(0L, Collections.<T>emptyList());
        }
        // get data
        List<T> datas = getSqlSession().selectList(sqlId(PAGING), criteria);
        return new Paging<T>(total, datas);
    }

    /**
     * 查询分页对象
     * @param offset 起始偏移
     * @param limit 分页大小
     * @param criteria Map查询条件
     * @return 查询到的分页对象
     */
    @Deprecated
    public Paging<T> paging2(Integer offset, Integer limit, Map<String, Object> criteria){
        if (criteria == null) {    //如果查询条件为空
            criteria = Maps.newHashMap();
        }
        // get total count
        Long total = getSqlSession().selectOne(sqlId(COUNT), criteria);
        if (total <= 0){
            return new Paging<T>(0L, Collections.<T>emptyList());
        }
        criteria.put(Constants.VAR_OFFSET, offset);
        criteria.put(Constants.VAR_LIMIT, limit);
        // get data
        List<T> datas = getSqlSession().selectList(sqlId(PAGING), criteria);
        return new Paging<T>(total, datas);
    }

    /**
     * 分页查询，offset， limit都丢在map里面
     * @param criteria 所有查询参数
     * @return 查询到的分页对象
     */
    @Deprecated
    public Paging<T> paging3(Map<String, Object> criteria) {
        if (criteria == null) {    //如果查询条件为空
            criteria = Maps.newHashMap();
        }
        // get total count
        Long total = getSqlSession().selectOne(sqlId(COUNT), criteria);
        if (total <= 0){
            return new Paging<T>(0L, Collections.<T>emptyList());
        }
        // get data
        List<T> datas = getSqlSession().selectList(sqlId(PAGING), criteria);
        return new Paging<T>(total, datas);
    }

    /**
     * sql语句的id
     * @param id sql id
     * @return "nameSpace.id"
     */
    protected String sqlId(String id){
        return nameSpace + "." + id;
    }
}
