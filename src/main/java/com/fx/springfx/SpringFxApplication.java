package com.fx.springfx;

import com.fx.springfx.application.JavaFxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringFxApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class);
    }

}
