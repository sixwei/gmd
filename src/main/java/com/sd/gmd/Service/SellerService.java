package com.sd.gmd.Service;

import com.sd.gmd.domain.Address;
import com.sd.gmd.domain.Items;
import com.sd.gmd.domain.Products;
import com.sd.gmd.domain.Sepro;
import com.sd.gmd.repository.SerllerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SellerService {

    @Autowired
    private SerllerRepository serllerRepository;

    public List<Products> getAllProducts(){
        return serllerRepository.getAllProducts();
    }


    /**
     * 对于产品管理
     *
     *
     */
    public List<Sepro> getAllSepro(){
        return serllerRepository.getAllSepro();
    }



    //进行sepro的编辑
    public Sepro getProductById(Integer pid, Integer uid){
        return serllerRepository.getProductById(pid,uid);
    }

    //添加到购物车
//    public boolean addCar(Integer pid,Integer uid,Integer stand){
//
//        /**
//         * 添加到购物车之前先把核对产品的剩余数量以及对厂商产品数量进行修改
//         */
//
//        Products product = serllerRepository.checkProductById(pid);
//        if (product.getResidue()==0){
//            return false;
//        }else{
//            //厂商的产品进行减少
//            Integer sellNumber = product.getSellnumber()+stand;
//            Integer residue = product.getResidue()-stand;
//            serllerRepository.changeNumber(pid,sellNumber,residue);
//            //进行购物车的添加
//            Items item = serllerRepository.checkItem(pid,uid);
//            if (item!=null){
//                Integer count = item.getCount()+stand;
//                serllerRepository.addCar(pid,uid,count);
//                return true;
//            }else{
//                Items items = new Items();
//                long l=System.currentTimeMillis();
//                Random random = new Random(l);
//                items.setIid(random.nextInt());
//                item.setUid(uid);
//                item.setPid(pid);
//                item.setCount(1);
//                serllerRepository.addCar(item);
//
//                return true;
//            }
//
//        }
//
//    }


    //添加到购物车 不对厂家的数据进行修改
    public void addCar(Integer pid,Integer uid,Integer stand){

        //进行购物车的添加
        List<Items> item1 = serllerRepository.checkItem(pid, uid);
        if (item1!=null&&item1.size()!=0){
            Items item=item1.get(0);
            Integer iid = item.getIid();
            Integer count = item.getCount()+stand;
            serllerRepository.addCar(iid,count);
        }else{
            Items items = new Items();
            items.setUid(uid);
            items.setPid(pid);
            items.setCount(stand);
            serllerRepository.addCar(items);

        }

    }


    /**
     * 购物车
     */
    //来到购物车得到全部的购物项
    public List<Items> toCarPage(Integer uid){
        return serllerRepository.getAllItems(uid);
    }


    //删除某一购物项  并且返回给卖家
    public void itemDelete(Integer uid,Integer pid){

        serllerRepository.itemDelete(uid, pid);

    }

    /**
     * 买单，判断用户是否有地址信息
     */

    public boolean checkAddressByUid(Integer uid){
        List<Address> addresses = serllerRepository.checkAddressByUid(uid);
        if (addresses!=null&&addresses.size()!=0){
            return true;
        }else
            return false;
    }

    //用户地址的添加和修改
    public Address addOrGetAddress(Address address){

        Address address1 = new Address();
        boolean b = checkAddressByUid(address.getUid());
        if (!b){
             address1 = serllerRepository.addAddress(address);
        }else {
             serllerRepository.changeAddress(address);
             address1 = serllerRepository.checkAddress(address.getUid()).get(0);

        }
        return address1;
    }



    //进行简单的查询
    public Address checkAddress(Integer uid){
        List<Address> addresses = serllerRepository.checkAddress(uid);

        if (addresses!=null&&addresses.size()!=0){
            Address address = addresses.get(0);
            return address;
        }else{
            return null;
        }

    }


    //每一类产品的修改
    public boolean successBuy(Integer pid,Integer uid,Integer stand){


        /**
         * 添加到购物车之前先把核对产品的剩余数量以及对厂商产品数量进行修改
         */

        Products product = serllerRepository.checkProductById(pid);
        if (product.getResidue()<stand){
            return false;
        }else{
            Integer sellNumber = product.getSellnumber()+stand;
            Integer residue = product.getResidue()-stand;
            //厂商products的修改
            serllerRepository.changeProducts(pid,sellNumber,residue);
            //购物车的修改
            serllerRepository.itemDelete(uid, pid);
            //卖家产品量进行修改   是否存在该产品
            //存在，进行修改
            //不存在，进行添加

            List<Sepro> sepros = serllerRepository.checkSeproByPid(pid);
            if (sepros!=null&&sepros.size()!=0){
                Sepro sepro = sepros.get(0);
                //更新sepro
                Integer total = sepro.getTotal()+stand;
                Integer residue2 = sepro.getResidue()+stand;
                sepro.setTotal(total);
                sepro.setResidue(residue2);
                serllerRepository.updateSeProByPid(sepro);
            }else{
                Sepro sepro = new Sepro();
                sepro.setPid(product.getPid());
                sepro.setUid(uid);
                sepro.setPrice(product.getPrice());
                sepro.setPname(product.getPname());
                sepro.setTotal(stand);
                sepro.setSellnum(0);
                sepro.setResidue(stand);
                serllerRepository.insertSepro(sepro);
            }

            return true;
        }


    }

}
