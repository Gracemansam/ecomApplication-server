package com.sam.converter;

import com.sam.dto.UserDto;
import com.sam.model.User;
import org.springframework.stereotype.Component;


    @Component
    public class UserConverter {


        public User convertDTOtoEntity(UserDto userDTO){
            return User.builder().
                    firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .email(userDTO.getEmail())
                    .mobile(userDTO.getMobile())
                    .build();

        }

        public UserDto convertEntityToDTO(User userEntity){

            return UserDto.builder()
                    .id(userEntity.getId())
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .email(userEntity.getEmail())
                    .mobile(userEntity.getMobile())
                    .build();
        }
    }
