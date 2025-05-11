package com.example.springkadaiform.controller;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.example.springkadaiform.entity.User;
import com.example.springkadaiform.form.ContactForm;
//import com.example.springkadaiform.service.UserService;

@Controller
public class ContactFormController {

    @GetMapping("/form")
    public String form(Model model) {

        // すでにインスタンスが存在する場合は行わない
        if (!model.containsAttribute("contactForm")) {
            // ビューにフォームクラスのインスタンスを渡す
            model.addAttribute("contactForm", new ContactForm());
        }

        return "contactFormView";
    }
    
    @GetMapping("/confirm")
    public String confirm(Model model) {
        // すでにインスタンスが存在する場合は行わない
        if (!model.containsAttribute("contactForm")) {
            return "redirect:/form";
        }
        return "confirmView"; // confirmView.htmlに対応
    }

    @PostMapping("/form")
    public String registerUser(RedirectAttributes redirectAttributes,
    		@Validated ContactForm form, BindingResult result) {
    	// バリデーションエラーがあったら終了
        if (result.hasErrors()) {
            // フォームクラスをビューに受け渡す
            redirectAttributes.addFlashAttribute("contactForm", form);
            // バリデーション結果をビューに受け渡す
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
                    + Conventions.getVariableName(form), result);

            return "redirect:/form";
        }
        
     // バリデーションが成功した場合に確認画面へリダイレクト
        redirectAttributes.addFlashAttribute("contactForm", form);
        return "redirect:/confirm";
    }
}