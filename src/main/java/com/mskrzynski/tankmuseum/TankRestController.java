package com.mskrzynski.tankmuseum;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigInteger;

@Controller //Why @Controller works, but @RestController doesn't?
public class TankRestController {

    private TankRepository tankRepository;
    private BigInteger newTankID;

    @Autowired
    public void setTankRepository(@Valid TankRepository tankRepository) {
        this.tankRepository = tankRepository;
    }

    @GetMapping("/")
    public String redirectToTanksList(){
        return "redirect:/tanks";
    }

    @GetMapping("/error")
    public String showError(){
        return "error";
    }

    @GetMapping("/tanks")
    public String tanksList(@Valid Model model) {
        model.addAttribute("tanksList", tankRepository.findAll(Sort.by("tankName")));
        if(newTankID != null){
            tankRepository.findById(newTankID).ifPresent(o -> model.addAttribute("newID", o)); //Taking object from Optional Class
        }
        return "tanks";
    }

    @PostMapping("/add")
    public String addTank(@Valid @RequestParam String tankName, @Valid @RequestParam String tankWeight) {
        Tank tank = new Tank();
        tank.setTankName(tankName);
        tank.setTankWeight(tankWeight);
        //Check if data is really empty, database does except empty-looking string (like space/multiple spaces) so we have to exclude them
        if(StringUtils.isBlank(tankName) || StringUtils.isBlank(tankWeight)){
            return "redirect:/error";
        }
        else {
            tankRepository.save(tank);
            newTankID = tank.getTankID();
            return "redirect:/tanks";

        }
    }

    //Thymeleaf creates HTML templates, HTML does not have a DELETE method, so need to use POST
    //If using something else, replace with RequestMethod.DELETE
    @RequestMapping(value="/remove", method = RequestMethod.POST)
    public String removeTank(@Valid @RequestParam(value = "tankID")BigInteger id) {
        Tank tank = tankRepository.findById(id).orElse(null);
        if(tank == null){
            return "redirect:/error";
        }
        else{
            tankRepository.delete(tank);
            return "redirect:/tanks";
        }
    }
}
