import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import io.javalin.Javalin;

public class Main {

    static Map<String, String> reservations = new HashMap<String, String>() {{
        put("saturday", "No reservation");
        put("sunday", "No reservation");
    }};

    public static void main(String[] args) {

        Javalin app = Javalin.create()
            .port(7777)
            .enableStaticFiles("/public")
            .start();

        app.post("/make-reservation", ctx -> {
            reservations.put(ctx.formParam("day"), ctx.formParam("time"));
            ctx.html("Your reservation has been saved");
        });

        app.get("/check-reservation", ctx -> {
            ctx.html(reservations.get(ctx.queryParam("day")));
        });

        app.post("/upload-example", ctx -> {
            ctx.uploadedFiles("files").forEach(file -> {
                try {
                    FileUtils.copyInputStreamToFile(file.getContent(), new File("upload/" + file.getName()));
                    ctx.html("Upload successful");
                } catch (IOException e) {
                    ctx.html("Upload failed");
                }
            });
        });

    }

}
