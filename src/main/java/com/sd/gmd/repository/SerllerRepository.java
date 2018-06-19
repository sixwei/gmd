package com.sd.gmd.repository;

import com.sd.gmd.config.MyJdbcTemplate;
import com.sd.gmd.domain.Address;
import com.sd.gmd.domain.Items;
import com.sd.gmd.domain.Products;
import com.sd.gmd.domain.Sepro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SerllerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

   MyJdbcTemplate myJdbcTemplate = new MyJdbcTemplate();

    //厂商的产品表
    RowMapper<Products> mapperProducts = new BeanPropertyRowMapper<>(Products.class);

    //卖家的产品表
    RowMapper<Sepro> mapperSepro = new BeanPropertyRowMapper<>(Sepro.class);

    //购物车
    RowMapper<Items> mapperItem = new BeanPropertyRowMapper<>(Items.class);

    //用户的地址信息
    RowMapper<Address> mapperAddress = new BeanPropertyRowMapper<>(Address.class);



    public List<Products> buyProductAll(){
        final String sql = "SELECT p.pid,b.bname,p.pname,p.price,p.pdescribe,p.total,p.sellnumber,p.residue from products AS p,brand AS b WHERE p.bid=b.bid";

        final String sql2 = "select * from product";
        List<Products> listProducts = jdbcTemplate.query(sql, mapperProducts);
        return listProducts;
    }

    public Sepro getProductById(Integer pid,Integer uid){
//        final String sql = "SELECT p.pid,b.bname,p.pname,p.price,p.pdescribe,p.total,p.sellnumber,p.residue from products AS p,brand AS b WHERE p.bid=b.bid and p.pid=?";
//        Products product = jdbcTemplate.queryForObject(sql, new Object[]{pid}, mapperProducts);
//        return product;
        final String sql = "select * from sepro where pid=? and uid=?";
        Sepro sepro = jdbcTemplate.queryForObject(sql, new Object[]{pid, uid}, mapperSepro);
        return sepro;


    }




    //对于进货页面，展示产商的产品列表

    public List<Products> getAllProducts(){
        final String sql = "SELECT p.pid,b.bname,p.pname,p.price,p.pdescribe,p.total,p.sellnumber,p.residue from products AS p,brand AS b WHERE p.bid=b.bid";

        final String sql2 = "select * from product";
        List<Products> listProducts = jdbcTemplate.query(sql, mapperProducts);
        return listProducts;
    }

    public Products buyProductById(Integer pid){
        final String sql = "SELECT p.pid,b.bname,p.pname,p.price,p.pdescribe,p.total,p.sellnumber,p.residue from products AS p,brand AS b WHERE p.bid=b.bid";
        Products products = jdbcTemplate.queryForObject(sql, mapperProducts);
        return products;
    }

    //添加购物车,updata
    public int addCar(Integer iid,Integer count){
        final String sql = "UPDATE items SET count = ? WHERE iid =? ";
        int update = jdbcTemplate.update(sql, new Object[]{count,iid});
        return update;
    }

    //某用户购物车没有此商品 insert
    public int addCar(Items item){
        final String sql = "insert into items(pid,uid,count) value(?,?,?)";
        int update = jdbcTemplate.update(sql, new Object[]{item.getPid(), item.getUid(), item.getCount()});
        return update;
    }

    //核对某一商品是否在购物车
    public List<Items> checkItem(Integer pid,Integer uid){
        final String sql = "select * from items where pid=? and uid =?";
        List<Items> items = jdbcTemplate.query(sql, new Object[]{pid, uid}, mapperItem);
        //Items item =items.get(0);
        return items;
    }

    /**
     * 查询厂商pid产品的剩余量
     */
    public Products checkProductById(Integer pid){
        final String sql = "select * from products where pid=?";
        Products product = jdbcTemplate.queryForObject(sql, new Object[]{pid}, mapperProducts);
        return product;
    }


    /**
     * 购物车
     */
    public List<Items> getAllItems(Integer uid){
        final String sql = "SELECT i.iid,i.pid, p.pname,p.price,i.count from items as i,products as p where i.pid=p.pid AND i.uid=?";
        List<Items> listItems = jdbcTemplate.query(sql, new Object[]{uid}, mapperItem);
        return listItems;
    }

    //删除某一购物项
    public int itemDelete(Integer uid,Integer pid){
        final String sql = "delete from items where uid=? and pid=?";
        int update = jdbcTemplate.update(sql, new Object[]{uid, pid});
        return update;

    }

    /**
     * 地址信息的判断，
     */

    public List<Address> checkAddressByUid(Integer uid){
        final String sql = "SELECT a.aid,u.username,a.address,a.phone  FROM address AS a,users AS u WHERE a.uid=u.uid AND u.uid=?";
        List<Address> listAddress = jdbcTemplate.query(sql, new Object[]{uid}, mapperAddress);
        return listAddress;
    }

    //添加新地址
    public Address addAddress(Address address){
        final String sql = "insert into address(uid,address,phone,othername) value(?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1,address.getUid());
                ps.setString(2,address.getAddress());
                ps.setString(3,address.getPhone());
                ps.setString(4,address.getOthername());
                return ps;

            }
        },keyHolder);
        if (rows>0){
            address.setAid(keyHolder.getKey().intValue());
            return address;
        }else{
            return null;
        }

    }

    //更新地址的信息
    public int changeAddress(Address address){
        final String sql = "update address set address=?,phone=?,othername=? where uid=?";
        int update = jdbcTemplate.update(sql, new Object[]{address.getAddress(), address.getPhone(), address.getOthername(), address.getUid()});

        return update;

    }

    //得到用户信息






    /**
     * 买单
     */
    //判断用户信息是否全面    放弃使用

    public List<Address> checkAddress(Integer uid){
        final String sql = "SELECT a.aid,a.othername,a.address,a.phone  FROM address AS a,users AS u WHERE a.uid=u.uid AND u.uid=?";
        List<Address> addresses = jdbcTemplate.query(sql, new Object[]{uid}, mapperAddress);
        return addresses;
    }

    //确认支付
    //products的修改
    public int changeProducts(Integer pid,Integer sellNumber,Integer residue){
        final String sql = "update products set sellNumber=?,residue=? where pid=?";
        int update = jdbcTemplate.update(sql, new Object[]{sellNumber, residue, pid});
        return update;
    }

    //items的修改   上面有方法


    //对sepro进行修改
    //查询某一产品sepro中是否已经存在
    public List<Sepro> checkSeproByPid(Integer pid){
        final String sql = "select * from sepro where pid = ?";
        List<Sepro> seproslist = jdbcTemplate.query(sql, new Object[]{pid}, mapperSepro);
        return seproslist;
    }

    //update  sepro
    public int updateSeProByPid(Sepro sepro){
        final String sql = "update sepro set total =?,sellnums=? where uid=? and pid =?";
        int update = jdbcTemplate.update(sql, new Object[]{sepro.getTotal(), sepro.getSellnum(), sepro.getUid(), sepro.getPid()});
        return update;
    }

    //insert sepro

    public int insertSepro(Sepro sepro){
        final String sql = "insert into sepro(uid,pid,pname,price,total,sellnums,residue) value(?,?,?,?,?,?,?)";
        int update = jdbcTemplate.update(sql, new Object[]{sepro.getUid(), sepro.getPid(),sepro.getPname(), sepro.getPrice(), sepro.getTotal(), sepro.getSellnum(), sepro.getResidue()});
        return update;
    }


    //得到所有的sepro
    public List<Sepro> getAllSepro(){
        final String sql = "select * from sepro";
        List<Sepro> seproList = jdbcTemplate.query(sql, mapperSepro);
        return seproList;
    }







}
