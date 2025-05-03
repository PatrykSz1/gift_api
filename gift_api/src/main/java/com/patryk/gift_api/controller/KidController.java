package com.patryk.gift_api.controller;

import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateGiftCommand;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.command.UpdateGiftCommand;
import com.patryk.gift_api.model.command.UpdateKidCommand;
import com.patryk.gift_api.model.dto.GiftDto;
import com.patryk.gift_api.model.dto.KidDto;
import com.patryk.gift_api.model.dto.SearchRequest;
import com.patryk.gift_api.service.GiftService;
import com.patryk.gift_api.service.KidSearchService;
import com.patryk.gift_api.service.KidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kids")
@RequiredArgsConstructor
public class KidController {

    private final KidService kidService;
    private final GiftService giftService;
    private final KidSearchService kidSearchService;


    @PostMapping
    public ResponseEntity<KidDto> addKid(@RequestBody CreateKidCommand createKidCommand) {
        return new ResponseEntity<>(kidService.addKid(createKidCommand), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<KidDto>> getAllKids() {
        return new ResponseEntity<>(kidService.getAllKids(), HttpStatus.OK);
    }

    //TODO paginacja


    @PostMapping("/search")
    public ResponseEntity<List<KidDto>> searchKids(@RequestBody SearchRequest searchRequest) {
        return new ResponseEntity<>(kidSearchService.searchKids(searchRequest), HttpStatus.OK);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<KidDto>> searchKids(@RequestParam(required = false) String name,
//                                                   @RequestParam(required = false) String gift) {
//        return new ResponseEntity<>(kidSearchService.searchKids(name, gift), HttpStatus.OK);
//    }

    @GetMapping("/{kidId}")
    public ResponseEntity<KidDto> getKidById(@PathVariable int kidId) {
        return new ResponseEntity<>(kidService.getKidById(kidId), HttpStatus.OK);
    }

    @PutMapping("/{kidId}")
    public ResponseEntity<KidDto> updateKid(@PathVariable int kidId,
                                            @RequestBody UpdateKidCommand updateKidCommand) {
        return new ResponseEntity<>(kidService.updateKid(updateKidCommand, kidId), HttpStatus.OK);
    }

    @DeleteMapping("/{kidId}")
    public ResponseEntity<Void> deleteKid(@PathVariable int kidId) {
        kidService.deleteKid(kidId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{kidId}/gifts")
    public ResponseEntity<GiftDto> addGiftToKid(@PathVariable int kidId,
                                                @RequestBody CreateGiftCommand createGiftCommand) {
        return new ResponseEntity<>(giftService.addGiftToKid(createGiftCommand, kidId), HttpStatus.CREATED);
    }

    @GetMapping("/{kidId}/gifts")
    public ResponseEntity<List<GiftDto>> getGiftsByKidId(@PathVariable int kidId) {
        return new ResponseEntity<>(giftService.getGiftsByKidId(kidId), HttpStatus.OK);
    }

    @PutMapping("/{kidId}/gifts/{giftId}")
    public ResponseEntity<GiftDto> updateGiftForKid(@PathVariable int kidId,
                                                    @PathVariable int giftId,
                                                    @RequestBody UpdateGiftCommand updateGiftCommand) {
        return new ResponseEntity<>(giftService.updateGiftForKid(kidId, giftId, updateGiftCommand), HttpStatus.OK);
    }

    @DeleteMapping("/{kidId}/gifts/{giftId}")
    public ResponseEntity<Void> deleteGift(@PathVariable int giftId) {
        giftService.deleteGift(giftId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}