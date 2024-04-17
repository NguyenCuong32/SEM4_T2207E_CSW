package com.t2207e.exam.service.impl;

import com.t2207e.exam.dtos.PlayerBuyItemDTO;
import com.t2207e.exam.dtos.request.RequestCreatePlayer;
import com.t2207e.exam.dtos.request.RequestPlayerBuyItem;
import com.t2207e.exam.dtos.response.ResponseStatus;
import com.t2207e.exam.entity.Item;
import com.t2207e.exam.entity.Player;
import com.t2207e.exam.repository.ItemRepository;
import com.t2207e.exam.repository.PlayerRepository;
import com.t2207e.exam.service.IPlayerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlayerService implements IPlayerService {

    final PlayerRepository playerRepository;
    final ItemRepository itemRepository;

    @Override
    @Transactional
    public ResponseStatus create(RequestCreatePlayer request) {
        Player player = Player
                .builder()
                .playerName(request.getPlayerName())
                .playerNational(request.getPlayerNational())
                .build();
        playerRepository.save(player);
        return ResponseStatus
                .builder()
                .status("True")
                .errorCode(Short.parseShort(String.valueOf(HttpStatus.OK.value())))
                .build();
    }

    @Override
    public ResponseStatus playerBuyItem(RequestPlayerBuyItem request) {
        Optional<Player> playerOptional = playerRepository.findById(request.getPlayerId());
        Optional<Item> itemOptional = itemRepository.findById(request.getItemId());
        if(itemOptional.isEmpty() || playerOptional.isEmpty()) {
            throw new RuntimeException("player or item information is incorrect");
        }
        Player player = playerOptional.get();
        List<Item> itemList = player.getItems();
        itemList.add(itemOptional.get());
        player.setItems(itemList);
        playerRepository.save(player);
        return ResponseStatus
                .builder()
                .status("True")
                .errorCode(Short.parseShort(String.valueOf(HttpStatus.OK.value())))
                .build();
    }

    @Override
    public List<PlayerBuyItemDTO> listPlayerBuyItem() {
        return null;
    }
}