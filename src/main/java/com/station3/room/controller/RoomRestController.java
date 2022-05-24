package com.station3.room.controller;

import com.station3.common.ConstantProperties;
import com.station3.common.dto.CommonResponse;
import com.station3.room.dto.RoomRequestDto;
import com.station3.room.dto.RoomResponseDto;
import com.station3.room.service.impl.impl.RoomServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
//@RequestMapping("/api/v1/room")
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
    @ApiOperation(value = "방 정보")
    @GetMapping(value = "/api/v1/room/{roomSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
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
     * @content : 방 > 수정
     * @name :
     * @date : 2022-05-23
     * @author : 신동아
     * @return :
     **/
    @ApiOperation(value = "방 수정")
    @PutMapping(value = "/api/v1/room/put", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody RoomRequestDto roomRequestDto) throws Exception {
        Integer resultUpdate = roomService.update(roomRequestDto.getRoomSeq(), roomRequestDto);
        return ResponseEntity.ok().body(resultUpdate);
    }

    /**
     * @content : 방 > 목록
     * @name :
     * @date : 2022-05-23
     * @author : 신동아
     * @return :
     **/
    @ApiOperation(value = "방 목록 정보")
    @PostMapping(value = "/api/v1/room/roomList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> roomList(@RequestBody RoomRequestDto roomRequestDto) throws Exception {

        String msg = "";
        List<RoomResponseDto> roomList = roomService.getRoomList(roomRequestDto);

        CommonResponse commonResponse = CommonResponse.builder()
                .data(roomList)
                .message(msg)
                .status(ConstantProperties.OUTPUT_SUCCESS)
                .build();

        return ResponseEntity.ok().body(commonResponse);
    }

    @PostMapping(value="api/v1/room/post/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String post(@RequestBody String text) throws Exception {
        return "Post 요청 => " + text;
    }

    /**
     * @content : 방 > 등록
     * @name :
     * @date : 2022-05-23
     * @author : 신동아
     * @return :
     **/
    @ApiOperation(value = "방 등록")
    @PostMapping(value = "/api/v1/room/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody RoomRequestDto roomRequestDto) throws Exception {
        Integer resultSave = roomService.save(roomRequestDto);

        return ResponseEntity.ok().body(resultSave);
    }
}
