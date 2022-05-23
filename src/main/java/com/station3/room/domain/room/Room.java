package com.station3.room.domain.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
	@Column(name="roomType")
	private String roomType;

	@ApiModelProperty(notes = "삭제여부", value = "Y/N")
	@Column(name="delFlag")
	private String delFlag;

	@Builder
	public Room(Integer roomSeq, String roomType, String delFlag){
		this.roomSeq = roomSeq;
		this.roomType = roomType;
		this.delFlag = delFlag;
	}

	public void update(String roomType){
		this.roomType = roomType;
	}
}

