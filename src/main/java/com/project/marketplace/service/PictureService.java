package com.project.marketplace.service;

import com.project.marketplace.model.Picture;
import com.project.marketplace.model.User;
import com.project.marketplace.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    public boolean uploadNewPicture(){
        return true;
    }

    @Transactional
    public void changePicturePriceById(float price, long id){
        pictureRepository.changePrice(price, id);
    }

    @Transactional
    public List<Picture> findOwnedPictures(User owner){
        return pictureRepository.findAllByOwner(owner);
    }

    @Transactional
    public List<Picture> findCreatedPictures(User creator){
        return pictureRepository.findAllByCreator(creator);
    }
}