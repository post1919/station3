package com.station3.room.domain.room;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserCompanyRepositoryTest {
    @Autowired
    RoomRepository roomRepository;

    @Transactional
    @Test
    public void 방정보_불러오기(){
        //given
        Integer roomSeq = 1;

        //when
        //Optional<UserCompany> userCompany = userCompanyRepository.findById(9);
        Room room = roomRepository.getById(roomSeq);

        //then
        assertThat(room.getRoomSeq(), is(equalTo(roomSeq)));
    }
}
