package com.scm.scm20.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.scm20.helpers.AppConstants;
import com.scm.scm20.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService{

    private Cloudinary cloudinary;

    //its a feature in spring that if we create a constructor, it will do the injection & for prod const injection is better
    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {

        //here uploading image

        try{
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", filename
            ));

            // and this return url

            return this.getUrlFromPublicId(filename);

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }


    public String getUrlFromPublicId(String publicId){

        return cloudinary
                .url()
                .transformation(
                    new Transformation<>()
                    .width(AppConstants.CONTACT_IMAGE_WIDTH)
                    .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                    .crop(AppConstants.CONTACT_IMAGE_CROP))
                .generate(publicId);

    }

}
