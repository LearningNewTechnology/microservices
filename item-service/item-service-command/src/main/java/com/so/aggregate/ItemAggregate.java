package com.so.aggregate;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import java.util.List;
import com.so.commands.CreateItemCommand;
import com.so.commands.ItemCommand;
import com.so.events.item.CreateItemEvent;

/**
 * Created by sergiu.oltean on 5/9/2017.
 */
public class ItemAggregate extends ReflectiveMutableCommandProcessingAggregate<ItemAggregate, ItemCommand> {

	private String itemCode;
	private int reservePrice;

	public List<Event> process(CreateItemCommand createItemCommand) {
		return EventUtil.events(new CreateItemEvent(createItemCommand.getItemCode(), createItemCommand.getReservePrice()));
	}

	public void apply(CreateItemEvent createItemEvent) {
		this.itemCode = createItemEvent.getItemCode();
		this.reservePrice = createItemEvent.getReservePrice();
	}

}
