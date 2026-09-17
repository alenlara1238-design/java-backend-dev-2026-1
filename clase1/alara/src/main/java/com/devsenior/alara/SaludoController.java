package com.devsenior.alara;

import org.springframework.web.bind.annotation.*;

@RestController // Indica que esta clase tendrá la capacidad de comunicarse con el servidor tomcat
public class SaludoController {

    @GetMapping("/")
    public String saludo() {
        return "Hola Mundo";
    }

    @GetMapping("/java")
    public String java() {
        return "Hola Java";
    }

    @GetMapping("/devsenior")
    public String devsenior() {
        return "Hola DevSenior";
    }
}
