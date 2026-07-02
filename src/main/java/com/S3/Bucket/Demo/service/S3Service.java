package com.S3.Bucket.Demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class S3Service implements FileServiceImpl {

    private final AmazonS3 s3;
    
//    @Value("${bucketName}")
    private String bucketName="datastoremerchant";

    public S3Service(AmazonS3 s3) {
        this.s3 = s3;
    }

    public String saveFile(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            s3.putObject(new PutObjectRequest(bucketName, originalFileName, file.getInputStream(), null));
            return "File uploaded successfully: " + originalFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }

    @Override
    public byte[] downloadFile(String filename) {
    	
    	ByteArrayOutputStream outputStream= new ByteArrayOutputStream();;
    	 try (S3Object s3Object = s3.getObject(bucketName, filename)) {
             S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
             byte[] buffer = new byte[1024];
             int bytesRead;
             try {
				while ((bytesRead = objectInputStream.read(buffer)) != -1) {
				     outputStream.write(buffer, 0, bytesRead);
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
         } catch (AmazonServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SdkClientException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    	 return outputStream.toByteArray();
    }

    @Override
    public String deletefile(String filename) {
        try {
            s3.deleteObject(new DeleteObjectRequest(bucketName, filename));
            return "File deleted successfully: " + filename;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to delete file: " + e.getMessage();
        }
    }

    @Override
    public List<String> listAllFiles() {
        try {
            return null;
        } catch (Exception e) {
            // Handle the error appropriately
            e.printStackTrace();
            return null;
        }
    }
}
