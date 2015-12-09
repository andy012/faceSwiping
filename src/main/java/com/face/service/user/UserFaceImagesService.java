package com.face.service.user;

import com.face.model.user.UserFaceImagesEntity;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by andy on 12/8/15.
 */

public interface UserFaceImagesService {
    public List<UserFaceImagesEntity> findAllByUserId(final Long userId);
    public UserFaceImagesEntity save(UserFaceImagesEntity userFaceImagesEntity);
    public void delete(UserFaceImagesEntity userFaceImagesEntity);
    public UserFaceImagesEntity findOneByImageKey(String imageKey);
    public void deleteAllByUserId(final Long userId);
    public void deleteOneByImageKey(final String imageKey);
}
