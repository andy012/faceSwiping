package com.face.service.user.impl;

import com.face.model.user.UserFaceImagesEntity;
import com.face.repository.user.UserFaceImagesEntityRepository;
import com.face.service.user.UserFaceImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by andy on 12/8/15.
 */

@Service
public class UserFaceImagesServiceImpl implements UserFaceImagesService{
    @Autowired
    private UserFaceImagesEntityRepository userFaceImagesEntityRepository;
    public List<UserFaceImagesEntity> findAllByUserId(final Long userId){
        return    userFaceImagesEntityRepository.findAllByUserId(userId);
    }

    @Override
    public UserFaceImagesEntity save(UserFaceImagesEntity userFaceImagesEntity) {
        return userFaceImagesEntityRepository.save(userFaceImagesEntity);
    }

    @Override
    public void delete(UserFaceImagesEntity userFaceImagesEntity) {
         userFaceImagesEntityRepository.delete(userFaceImagesEntity);
    }

    @Override
    public UserFaceImagesEntity findOneByImageKey(String imageKey) {
        return userFaceImagesEntityRepository.findOneByImageKey(imageKey);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        userFaceImagesEntityRepository.deleteAllByUserId(userId);
    }

    @Override
    public void deleteOneByImageKey(String imageKey) {
        userFaceImagesEntityRepository.deleteOneByImageKey(imageKey);
    }

}
