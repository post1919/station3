package com.station3.room.domain.room;

import com.station3.room.type.TradeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(schema="example", catalog="example", name="room_info")
@NoArgsConstructor
public class RoomInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "순번", value = "순번")
    @Column(name="roomInfoSeq")
    private Integer roomInfoSeq;

    @ApiModelProperty(notes = "거래구분", value = "거래구분")
    //@Enumerated(EnumType.STRING)
    @Column(name="tradeType", nullable = false)
    private TradeType tradeType;

    @ApiModelProperty(notes = "가격", value = "가격")
    @Column(name="price", nullable = false)
    private BigDecimal price;

    @ApiModelProperty(notes = "삭제여부", value = "Y/N")
    @Column(name="delFlag", nullable = false)
    private String delFlag;

    @ManyToOne
    @JoinColumn(name = "roomSeq")
    private Room room;

    @Builder
    public RoomInfo(Integer roomInfoSeq, TradeType tradeType, BigDecimal price, String delFlag){
        this.roomInfoSeq = roomInfoSeq;
        this.tradeType = tradeType;
        this.price = price;
        this.delFlag = delFlag;
    }

    public void update(TradeType tradeType, BigDecimal price, String delFlag){
        this.tradeType = tradeType;
        this.price = price;
        this.delFlag = delFlag;
    }
}