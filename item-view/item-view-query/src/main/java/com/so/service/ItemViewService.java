package com.so.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.so.model.ItemState;
import com.so.repository.ItemViewRepository;
import com.so.view.ItemView;

/**
 * Created by sergiu.oltean on 5/12/2017.
 */
@Service
public class ItemViewService {

	private ItemViewRepository itemViewRepository;

	@Autowired
	public ItemViewService(ItemViewRepository itemViewRepository) {
		this.itemViewRepository = itemViewRepository;
	}

	public void createItem(ItemView itemView) {
		itemViewRepository.save(itemView);
	}

	public void updateItemState(String itemCode, ItemState itemState) {
		ItemView item = itemViewRepository.findOne(itemCode);
		item.setItemState(itemState);
	}

	public List<ItemView> findAllItems() {
		return itemViewRepository.findAll();
	}

}
