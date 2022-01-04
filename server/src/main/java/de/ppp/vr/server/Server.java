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
        new Thread(MainUI::init).start();
        SpringApplication.run(Server.class, args);
    }

    private static final String template = "Hello, %s!";

    @PostMapping("/sendcoords")
    public String greeting(@RequestBody String a) {
        Map< String, Object > map = JsonParserFactory.getJsonParser().parseMap(a);
        Coords coords = new Coords((Double) map.get("x"), (Double) map.get("y"));
        MainUI.screenBuffer[(int) coords.x][(int) coords.y] = 1;
        return "got " + coords.x + " " + coords.y;
    }

    public record Coords(double x, double y) {
    }

}
