package com.personaltrainer.controller;

import com.personaltrainer.model.ChargeRequest;
import com.personaltrainer.model.User;
import com.personaltrainer.service.StripeService;
import com.personaltrainer.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller

public class PaymentController {

    @Autowired
    Environment environment;

    @Autowired
    UserService userService;

    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model, @RequestParam int userId, @RequestParam int number) {
        model.addAttribute("amount", number * 100);
        model.addAttribute("stripePublicKey", environment.getProperty("STRIPE_PUBLIC_KEY"));
        model.addAttribute("currency", ChargeRequest.Currency.PLN);
        session.setAttribute("userId", userId);
        session.setAttribute("number", number);
        return "checkout";
    }

    @Autowired
    private StripeService paymentsService;

    @PostMapping("/charge")
    public String charge(HttpSession session, ChargeRequest chargeRequest, Model model)
            throws StripeException {
        chargeRequest.setCurrency(ChargeRequest.Currency.PLN);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("status", charge.getStatus());

        if (charge.getStatus().equals("succeeded")) {
            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            double number = Double.parseDouble(session.getAttribute("number").toString());
            User user = userService.findById(userId);
            user.setBalance(user.getBalance() + number);
            userService.save(user);
        }

        session.removeAttribute("userId");
        session.removeAttribute("number");

        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
