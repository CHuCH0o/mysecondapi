package co.edu.umanizales.mysecondapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/seller")
public class SellerController {

    @GetMapping
    public String getSeller() {
        return "RobertoCarlos";
    }
}
