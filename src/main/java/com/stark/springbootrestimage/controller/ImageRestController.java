package com.stark.springbootrestimage.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/images")
public class ImageRestController {

    @GetMapping("/show")
    public void showImages(@RequestParam(value ="name",defaultValue="iron-man-color") String name, HttpServletResponse response) throws IOException {
        ClassPathResource imageFile = new ClassPathResource("/static/"+name+".png");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setHeader("Content-Disposition", "inline; filename=\""+name+".jpg\"");
        StreamUtils.copy(imageFile.getInputStream(), response.getOutputStream());
    }

    @GetMapping("/download")
    public void downloadImage(@RequestParam(value="name", defaultValue="iron-man-color") String name, HttpServletResponse response) throws IOException {
        ClassPathResource imageFile = new ClassPathResource("/static/"+name+".png");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=\""+name+".jpg\"");
        StreamUtils.copy(imageFile.getInputStream(), response.getOutputStream());
    }

    @GetMapping(
            value = "/get-image-with-media-type",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/iron-man-color.png");
        return getBytesFromInputStream(in);
    }

    private static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }

}
