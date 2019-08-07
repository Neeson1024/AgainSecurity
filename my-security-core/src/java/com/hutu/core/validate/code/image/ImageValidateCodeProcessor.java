package com.hutu.core.validate.code.image;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import com.hutu.core.validate.code.impl.*;
import javax.imageio.ImageIO;
import java.io.IOException;

@Component
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) {
        try {
            ImageIO.write(validateCode.getImage() ,"JPEG",request.getResponse().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
