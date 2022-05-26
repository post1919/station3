package com.station3.room.domain.room;

import com.station3.room.type.TradeType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomCustomRepository {
    List<Room> findAllInnerFetchJoin(TradeType tradeType);

    /*@PersistenceContext
    private EntityManager entityManager;

    public List<BookRentalEntity> listBookRentalByBookId(UUID bookId) {
        JPAQuery<?> query = new JPAQuery<>(entityManager);

        QBookEntity qBookEntity = QBookEntity.bookEntity;
        QBookRentalEntity qBookRentalEntity = QBookRentalEntity.bookRentalEntity;

        List<Room> fetched = query.select(qBookRentalEntity)
                .from(qBookRentalEntity)
                .leftJoin(qBookEntity)
                .on(qBookRentalEntity.bookEntity.id.eq(qBookEntity.id))
                .where(qBookRentalEntity.bookEntity.id.eq(bookId))
                .fetch();

        return fetched;
    }*/
}
