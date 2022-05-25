package com.station3.room.controller;

import com.station3.common.ConstantProperties;
import com.station3.common.dto.CommonResponse;
import com.station3.room.dto.RoomRequestDto;
import com.station3.room.dto.RoomResponseDto;
import com.station3.room.service.impl.impl.RoomServiceImpl;
import com.station3.room.type.RoomType;
import com.station3.room.type.TradeType;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
@RestController
@ApiOperation(value="회원가입", notes = "회원가입")
public class RoomRestController {

    private final RoomServiceImpl roomService;

    /**
     * @content : 방 > 하나
     * @name :
     * @date : 2022-05-23
     * @author : 신동아
     * @return :
     **/
    @ApiOperation(value = "방 정보", notes = "내방을 하나 조회한다.")
    @GetMapping(value = "/{roomSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> room(@PathVariable(name="roomSeq") Integer roomSeq) throws Exception {

        String msg = "";
        RoomResponseDto room = roomService.getRoom(roomSeq);

        CommonResponse commonResponse = CommonResponse.builder()
                .data(room)
                .message(msg)
                .status(ConstantProperties.OUTPUT_SUCCESS)
                .build();

        return ResponseEntity.ok().body(commonResponse);
    }

    /**
     * @content : 전체방 목록을 조회한다. / 내방 목록을 조회한다.
     *            검색조건 : 방 유형, 거래 유형, 가격 범위
     * @name : 방 > 목록
     * @date : 2022-05-23
     * @author : 신동아
     * @return :
     **/
    @ApiOperation(value = "방 목록 정보", notes = "목록을 조회한다.")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(
            @RequestParam(required = false, value = "keyword") String keyword
            , @RequestParam(required = false, value = "roomType", defaultValue = "") String roomType
            , @RequestParam(required = false, value = "tradeType", defaultValue = "") String tradeType
    ) throws Exception {

        String msg = "";
        RoomRequestDto roomRequestDto = RoomRequestDto.builder()
                .keyword(keyword)
                .roomType(RoomType.getByCode(roomType))
                .tradeType(TradeType.getByCode(tradeType))
                .build();
        List<RoomResponseDto> roomList = roomService.getRoomList(roomRequestDto);

        CommonResponse commonResponse = CommonResponse.builder()
                .data(roomList)
                .message(msg)
                .status(ConstantProperties.OUTPUT_SUCCESS)
                .build();

        return ResponseEntity.ok().body(commonResponse);
    }

    /**
     * @content : 방 > 등록
     * @name :
     * @date : 2022-05-23
     * @author : 신동아
     * @return :
     **/
    @ApiOperation(value = "방 등록", notes = "내방을 등록할 수 있다.")
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody RoomRequestDto roomRequestDto) throws Exception {
        Integer resultSave = roomService.save(roomRequestDto);

        return ResponseEntity.ok().body(resultSave);
    }

    /**
     * @content : 방 > 수정
     * @name :
     * @date : 2022-05-23
     * @author : 신동아
     * @return :
     **/
    @ApiOperation(value = "방 수정", notes = "내방을 수정할수 있다.")
    @PutMapping(value = "/{roomSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable(name="roomSeq") Integer roomSeq, @RequestBody RoomRequestDto roomRequestDto) throws Exception {
        Integer resultUpdate = roomService.update(roomSeq, roomRequestDto);
        return ResponseEntity.ok().body(resultUpdate);
    }

    /**
     * @content : 방 > 삭제
     * @name :
     * @date : 2022-05-25
     * @author : 신동아
     * @return :
     **/
    @ApiOperation(value = "방 삭제", notes = "내방을 삭제할수 있다.")
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestBody RoomRequestDto roomRequestDto) throws Exception {
        roomService.delete(roomRequestDto);

        return ResponseEntity.ok().body("");
    }
}
