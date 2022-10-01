package com.smartedulanka.finalyearproject.imageModeration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

@Service
public class ImageModeration {

    public String ImageModerate(MultipartFile file)  throws IOException{

        byte [] byteArr = null;

        try {
            byteArr = file.getBytes();

        } catch (IOException e) {
            System.err.println("Failed to load image: " + e.getMessage());

        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArr);

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        DetectModerationLabelsRequest request = new DetectModerationLabelsRequest()
                .withImage(new Image().withBytes(byteBuffer))
                .withMinConfidence(60F);

        try
        {
            DetectModerationLabelsResult result = rekognitionClient.detectModerationLabels(request);
            List<ModerationLabel> labels = result.getModerationLabels();
            System.out.println("Detected labels for " );
            for (ModerationLabel label : labels)
            {
                System.out.println("Label: " + label.getName()
                        + "\n Confidence: " + label.getConfidence().toString() + "%"
                        + "\n Parent:" + label.getParentName());

                if(label.getName().equals("Nudity")||label.getName().equals("Graphic Male Nudity")||label.getName().equals("Graphic Female Nudity")||label.getName().equals("Adult Toys")||label.getName().equals("Sexual Activity")||label.getName().equals("Female Swimwear Or Underwear")||label.getName().equals("Male Swimwear Or Underwear")||label.getName().equals("Sexual Situations")||label.getName().equals("Partial Nudity")||label.getName().equals("Hanging")||label.getName().equals("Physical Violence")||label.getName().equals("Alcoholic Beverages")||label.getName().equals("Weapon Violence")||label.getName().equals("Drinking")||label.getName().equals("Drug Use")||label.getName().equals("Drug Products")||label.getName().equals("Drug Paraphernalia")||label.getName().equals("Corpses")||label.getName().equals("Emaciated Bodies")||label.getName().equals("Self Injury")||label.getName().equals("Extremist")||label.getName().equals("Explosions And Blasts")||label.getName().equals("Air Crash")||label.getName().equals("Revealing Clothes")||label.getName().equals("Barechested Male")||label.getName().equals("Graphic Violence Or Gore")||label.getName().equals("White Supremacy")||label.getName().equals("Illustrated Explicit Nudity")||label.getName().equals("Tobacco Products")||label.getName().equals("Suggestive")||label.getName().equals("Violence")||label.getName().equals("Smoking")||label.getName().equals("Visually Disturbing")||label.getName().equals("Rude Gestures")||label.getName().equals("Weapons")||label.getName().equals("Gambling")||label.getName().equals("Pills")){

                    return "Offensive";

                }else{

                    return "NotOffensive";

                }
            }
        }
        catch (AmazonRekognitionException e)
        {
            e.printStackTrace();

        }
        return "NotOffensive";



    }


}
