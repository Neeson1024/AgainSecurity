package com.hutu.core.validate.image;

import com.hutu.core.validate.ImageCode;
import com.hutu.core.validate.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

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
