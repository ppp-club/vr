package de.ppp.vr.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class Server {

    public static void main(String[] args) {
        MainUI.init();
        SpringApplication.run(Server.class, args);
    }

    private static final String template = "Hello, %s!";

    @PostMapping("/sendcoords")
    public String greeting(@RequestBody Coords coords) {
        MainUI.screenBuffer[(int) coords.x][(int) coords.y] = 1;
        MainUI.update();
        return "got " + coords.x + " " + coords.y;
    }

    public record Coords(double x, double y) {
    }

}
