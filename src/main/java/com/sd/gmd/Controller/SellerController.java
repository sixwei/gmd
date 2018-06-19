package com.sd.gmd.Controller;

import com.sd.gmd.Service.SellerService;
import com.sd.gmd.domain.Address;
import com.sd.gmd.domain.Items;
import com.sd.gmd.domain.Products;
import com.sd.gmd.domain.Sepro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("/seller/pro")
    public String getAllPro(Model model){
        List<Products> allProducts = sellerService.getAllProducts();
        model.addAttribute("pros",allProducts);
        return "seller/product";
    }

    @GetMapping("/seller/{pid}")
    public String toEditPage(@PathVariable("pid") Integer pid, Model model, HttpSession session){

        Integer uid = (Integer) session.getAttribute("uid");

        Sepro product = sellerService.getProductById(pid,uid);
        model.addAttribute("product",product);
        return "seller/edit";
    }

    //卖家进货页面
    @GetMapping("/seller/buypage")
    public String toBuyPage(Model model){
        List<Products> allProducts = sellerService.getAllProducts();
        model.addAttribute("pros",allProducts);
        return "seller/buypage";
    }

    //加入购物车
    @GetMapping("/seller/addcar/{pid}")
    public String addCar(@PathVariable("pid") Integer pid,HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");
        Integer stand =1;
        sellerService.addCar(pid,uid,stand);

        return "redirect:/seller/buypage";
    }

    /**
     * 对于卖家进货的时候购物车的设计
     */
    //来到购物车  查询出用户的所有购物项
    @GetMapping("/seller/carpage")
    public String toCarPage(Model model,HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");
        List<Items> items = sellerService.toCarPage(uid);
        model.addAttribute("items",items);
        double totalmoney = 0.0;
        for (Items item:items){
            double count = item.getPrice().doubleValue()*item.getCount();
            totalmoney+=count;
        }
        model.addAttribute("totalMoney",totalmoney);

        return "seller/carpage";

    }

    //购物车中添加和减少按钮的设计
    @GetMapping("/seller/itemchange")
    public String changeCarPage(@RequestParam Integer pid, @RequestParam Integer stand, HttpSession session){

        Integer uid = (Integer) session.getAttribute("uid");
        sellerService.addCar(pid,uid,stand);

        return "redirect:/seller/carpage";

    }

    //删除操作根据item.pid
    @GetMapping("/seller/delete/{pid}")
    public String itemDelete(@PathVariable Integer pid,HttpSession session){

        Integer uid = (Integer) session.getAttribute("uid");
        sellerService.itemDelete(uid,pid);

        return "redirect:/seller/carpage";


    }

    //买点进行支付
    //计划利用矩阵参数进行购物车的选择
    @GetMapping("/seller/buy")
    public String tobuy(HttpSession session,Model model){

        Integer uid = (Integer) session.getAttribute("uid");

        //先判断是否有用户的电话地址信息
        Address addresses = sellerService.checkAddress(uid);
        if (addresses!=null){
            model.addAttribute("address",addresses);
            //一种清空购物车
            List<Items> items = sellerService.toCarPage(uid);
            model.addAttribute("items",items);
            //总钱数
            double totalmoney = 0.0;
            for (Items item:items){
                double count = item.getPrice().doubleValue()*item.getCount();
                totalmoney+=count;
            }
            model.addAttribute("totalMoney",totalmoney);

            //return "seller/buy";

            return "seller/buy";
        }else{
            return "seller/addresspage";
        }



        //一种根据产品的pid进行买单

        //一种清空购物车

    }

    /**
     * 添加地址，addresspage页面的相关操作
     *
     * @return
     */
    @PostMapping("/seller/address")
    public String address(Address address,HttpSession session){

        Integer uid = (Integer) session.getAttribute("uid");
        address.setUid(uid);

        Address address1 = sellerService.addOrGetAddress(address);

        return "redirect:/seller/buy";
    }

    @GetMapping("/seller/toaddress")
    public String toaddress(HttpSession session,Model model){
        Integer uid = (Integer) session.getAttribute("uid");

        //先判断是否有用户的电话地址信息
        Address addresses = sellerService.checkAddress(uid);
        model.addAttribute("address", addresses);

        return "seller/addresspage";
    }


    /**
     * 点击确认支付
     * 减少厂商的产品
     * 清除购物车
     * 增加卖家的产品
     * 进入完成支付界面
     * @Param stand,
     *
     */
    @GetMapping("/seller/success")
    public String toSuccess(HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");

        List<Items> items = sellerService.toCarPage(uid);
        for (Items item:items){
            Integer pid = item.getPid();
            Integer stand = item.getCount();
            boolean b = sellerService.successBuy(pid, uid, stand);

            if (!b){
                return "seller/error";
            }
        }
        return "seller/success";

    }


}
