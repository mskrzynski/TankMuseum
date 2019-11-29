package com.mskrzynski.tankmuseum;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigInteger;

@Controller //Why @Controller works, but @RestController doesn't?
public class TankRestController {

    private TankRepository tankRepository;
    private BigInteger newTankID;

    @Autowired
    public void setTankRepository(TankRepository tankRepository) {
        this.tankRepository = tankRepository;
    }

    @RequestMapping("/")
    public String redirectToTanksList(){
        return "redirect:/tanks";
    }

    @RequestMapping("/tanks")
    public String tanksList(Model model) {
        model.addAttribute("tanksList", tankRepository.findAll(Sort.by("tankName")));
        if(newTankID != null){
            tankRepository.findById(newTankID).ifPresent(o -> model.addAttribute("newID", o)); //Taking object from Optional Class
        }
        return "tanks";
    }

    @RequestMapping("/add")
    public String addTank(@RequestParam String tankName, @RequestParam String tankWeight) {
        Tank tank = new Tank();
        tank.setTankName(tankName);
        tank.setTankWeight(tankWeight);
        if(!StringUtils.isBlank(tankName) && !StringUtils.isBlank(tankWeight)) tankRepository.save(tank);
        newTankID = tank.getTankID();
        return "redirect:/tanks";
    }

    @RequestMapping("/remove")
    public String removeTank(@RequestParam(value = "tankID")BigInteger id) {
        Tank product = tankRepository.findById(id).orElse(null);
        if(id != null) tankRepository.delete(product);
        return "redirect:/tanks";
    }
}
