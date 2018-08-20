import io.javalin.Javalin
import io.javalin.core.util.FileUtil

val reservations = mutableMapOf<String?, String?>(
        "saturday" to "No reservation",
        "sunday" to "No reservation"
)

fun main(args: Array<String>) {

    val app = Javalin.create().apply {
        port(7070)
        enableStaticFiles("/public")
    }.start()

    app.post("/make-reservation") { ctx ->
        reservations[ctx.formParam("day")] = ctx.formParam("time")
        ctx.html("Your reservation has been saved")
    }

    app.get("/check-reservation") { ctx ->
        ctx.html(reservations[ctx.queryParam("day")]!!)
    }

    app.post("/upload-example") { ctx ->
        ctx.uploadedFiles("files").forEach { (_, content, name) ->
            FileUtil.streamToFile(content, "upload/$name")
            ctx.html("Upload complete")
        }
    }

}
