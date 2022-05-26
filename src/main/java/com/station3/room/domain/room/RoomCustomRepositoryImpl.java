package com.station3.room.domain.room;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.station3.room.type.TradeType;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.station3.room.domain.room.QRoom.room;
import static com.station3.room.domain.room.QRoomInfo.roomInfo;

@Repository
public class RoomCustomRepositoryImpl implements RoomCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public RoomCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Room> findAllInnerFetchJoin(TradeType tradeType) {
        /*return jpaQueryFactory.selectFrom(room)
                .innerJoin(room.roomSeq, roomInfo)
                .fetchJoin()
                .fetch();
        */
        return jpaQueryFactory.select(room)
                .from(room)
                .leftJoin(room.roomInfos, roomInfo)
                //.on(roomInfo.tradeType.eq(tradeType))
                .where(roomInfo.tradeType.eq(tradeType))
                .fetchJoin()
                .fetch();
    }

    /*@Override
    public List<Post> findAllInnerFetchJoinWithDistinct() {
        return jpaQueryFactory.selectFrom(post)
                .distinct()
                .innerJoin(post.comments)
                .fetchJoin()
                .fetch();
    }*/
}
