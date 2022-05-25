package com.station3.room.domain.room;

import com.station3.room.type.RoomType;
import com.station3.room.type.TradeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(schema="example", catalog="example", name="room")
@NoArgsConstructor
@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "순번", value = "순번")
	@Column(name="roomSeq")
	private Integer roomSeq;

	@ApiModelProperty(notes = "방구분", value = "방구분")
	@Enumerated(EnumType.STRING)
	@Column(name="roomType", nullable = false)
	private RoomType roomType;

	@ApiModelProperty(notes = "삭제여부", value = "Y/N")
	@Column(name="delFlag", nullable = false)
	private String delFlag;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "roomSeq")
	private List<RoomInfo> roomInfos = new ArrayList<>();

	/*public void setRoomInfo(List<RoomInfo> roomInfo) {
		this.roomInfo = roomInfo;
	}

	public void addRoomInfo(RoomInfo p){
		if( roomInfo == null ){
			this.roomInfo = new ArrayList<>();
		}

		this.roomInfo.add(p);
	}*/

	@Builder
	public Room(Integer roomSeq, RoomType roomType, String delFlag/*, TradeType tradeType*/){
		this.roomSeq = roomSeq;
		this.roomType = roomType;
		this.delFlag = delFlag;
//		this.addRoomInfo(RoomInfo.builder().tradeType(tradeType).build());
	}

	public void update(RoomType roomType, String delFlag){
		this.roomType = roomType;
		this.delFlag = delFlag;
	}
}
