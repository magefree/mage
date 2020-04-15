package mage.cards.v;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.HumanSoldierToken;
import mage.watchers.Watcher;

/**
 * 
 * @author Emigara
 *
 */
public class ValiantRescuer extends CardImpl{

	public ValiantRescuer(UUID ownerId, CardSetInfo setInfo) {
		super(ownerId, setInfo, new CardType[] {CardType.CREATURE}, "{1}{W}");
		
		this.subtype.add(SubType.HUMAN);
		this.subtype.add(SubType.SOLDIER);
		
		this.power = new MageInt(3);
		this.toughness = new MageInt(1);
		
		//Whenever you cycle another card for the first time each turn, create a 1/1 white Human Soldier creature token.
		this.addAbility(new ValiantRescuerTriggeredAbility());
		
		this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
	}
	
	public ValiantRescuer(final ValiantRescuer card) {
		super(card);
	}

	@Override
	public Card copy() {
		return new ValiantRescuer(this);
	}

}

class ValiantRescuerTriggeredAbility extends TriggeredAbilityImpl{

	public ValiantRescuerTriggeredAbility() {
		super(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanSoldierToken()), false);
		this.addWatcher(new ValiantRescuerWatcher());
	}
	
	public ValiantRescuerTriggeredAbility(ValiantRescuerTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public boolean checkEventType(GameEvent event, Game game) {
		return event.getType() == GameEvent.EventType.CYCLE_CARD;
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            ValiantRescuerWatcher watcher = game.getState().getWatcher(ValiantRescuerWatcher.class);
            return watcher != null && watcher.getTimesCycled(getControllerId()) == 1;
        }
		return false;
	}

	@Override
	public ValiantRescuerTriggeredAbility copy() {
		// TODO Auto-generated method stub
		return new ValiantRescuerTriggeredAbility(this);
	}
	
	@Override
    public String getRule() {
        return "Whenever you cycle another card for the first time each turn, create a 1/1 white Human Soldier creature token.";
    }
	
}

class ValiantRescuerWatcher extends Watcher{

	private final Map<UUID, Integer> timesCycled = new HashMap<>();
	
	public ValiantRescuerWatcher() {
		super(WatcherScope.GAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void watch(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.CYCLE_CARD) {
            timesCycled.put(event.getPlayerId(), getTimesCycled(event.getPlayerId()) + 1);
        }
	}
	
    @Override
    public void reset() {
        super.reset();
        timesCycled.clear();
    }
	
	public int getTimesCycled(UUID playerId) {
        return timesCycled.getOrDefault(playerId, 0);
    }
	
}
