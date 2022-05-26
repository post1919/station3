package com.station3.room.domain.room;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoomRepositoryTest {

    /*@Autowired
    EntityManager testEntityManager;

    @Test
    void queryDsl_findPostsByMyCriteria_Three() {
        EntityManager entityManager = testEntityManager.getEntityManager();

        JPAQuery<Room> query = new JPAQuery<>(entityManager);
        QRoom qRoom = new QRoom("p");

        List<Room> rooms = query.from(qRoom)
                .where(qRoom.content.contains("hi")
                        .and(qRoom.comments.size().gt(0))
                ).orderBy(qRoom.id.desc())
                .fetch();

        assertThat(rooms).hasSize(3);
    }*/
}
