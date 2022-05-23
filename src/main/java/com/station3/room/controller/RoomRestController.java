package com.station3.room.controller;

import com.station3.common.ConstantProperties;
import com.station3.common.dto.CommonResponse;
import com.station3.room.dto.RoomReqResDto;
import com.station3.room.dto.RoomResponseDto;
import com.station3.room.service.impl.impl.RoomServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping(value="/api/v1/room", method = {RequestMethod.POST, RequestMethod.GET})
@RestController
@ApiOperation(value="회원가입", notes = "회원가입")
public class RoomRestController {

    private final RoomServiceImpl roomService;

    /**
     * @content : 방 목록
     * @name :
     * @date : 2022-05-23
     * @author : 신동아
     * @return :
     **/
    @ApiOperation(value = "방 정보")
    @GetMapping(value = "/{roomSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoom(@PathVariable(name="roomSeq") Integer roomSeq) throws Exception {

        String msg = "";
        RoomResponseDto room = roomService.getRoom(roomSeq);

        CommonResponse commonResponse = CommonResponse.builder()
                .data(room)
                .message(msg)
                .status(ConstantProperties.OUTPUT_SUCCESS)
                .build();

        return ResponseEntity.ok().body(commonResponse);
    }
}
