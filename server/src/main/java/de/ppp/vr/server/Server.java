package de.ppp.vr.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@SpringBootApplication
@RestController
public class Server {

    public static void main(String[] args) {
        logIPs();
        MainUI.init();
        SpringApplication.run(Server.class, args);
    }

    static void logIPs() {
        try {
            System.out.println("Your Host addr: " + InetAddress.getLocalHost().getHostAddress());  // often returns "127.0.0.1"
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface e = enumeration.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                while (a.hasMoreElements()) {
                    InetAddress addr = a.nextElement();
                    System.out.println("  " + addr.getHostAddress());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @PostMapping("/sendcoords")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public String greeting(@RequestBody Coords coords) {
        if(coords.x >= MainUI.WIDTH || coords.y >= MainUI.HEIGHT) return "coords out of bounds " + coords;
        MainUI.screenBuffer[(int) coords.x][(int) coords.y] = 1;
        MainUI.update();
        return "got " + coords.x + " " + coords.y;
    }

    @GetMapping("/bounds")
    public Bounds bounds() {
        return new Bounds(MainUI.WIDTH, MainUI.HEIGHT);
    }

    public record Bounds(int width, int height) {
    }

    public record Coords(double x, double y) {
    }
}
