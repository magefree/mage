package mage.cards.r;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.HumanSoldierToken;
import mage.watchers.Watcher;
import mage.watchers.common.CardsCycledOrDiscardedThisTurnWatcher;

public class RielleTheEverwise extends CardImpl{

	public RielleTheEverwise(UUID ownerId, CardSetInfo setInfo) {
		super(ownerId, setInfo, new CardType[]{CardType.CREATURE},"{1}{U}{R}");
		
		this.addSuperType(SuperType.LEGENDARY);
		
		this.subtype.add(SubType.HUMAN);
		this.subtype.add(SubType.WIZARD);
		
		this.power = new MageInt(0);
		this.toughness = new MageInt(3);
		
		this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerSourceEffect(
                new CardsInControllerGraveyardCount(new FilterInstantOrSorceryCard("instant and sorcery cards")), Duration.EndOfGame)));
		
		this.addAbility(new RielleTheEverwiseTriggeredAbility(ownerId));
		
	}
	
	public RielleTheEverwise(final RielleTheEverwise card) {
        super(card);
    }

	@Override
	public Card copy() {
		return new RielleTheEverwise(this);
	}
	
	class RielleTheEverwiseTriggeredAbility extends TriggeredAbilityImpl{

		public RielleTheEverwiseTriggeredAbility(UUID playerId) {
			super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(new RielleTheEverwiseWatcher().getTimesDiscarded(playerId)), false);
			this.addWatcher(new RielleTheEverwiseWatcher());
			
			
		}
		
		public RielleTheEverwiseTriggeredAbility(RielleTheEverwiseTriggeredAbility ability) {
			super(ability);
		}

		@Override
		public boolean checkEventType(GameEvent event, Game game) {
			return event.getType() == GameEvent.EventType.DISCARD_CARD;
		}

		@Override
		public boolean checkTrigger(GameEvent event, Game game) {
	        if (event.getPlayerId().equals(getControllerId())) {
	        	RielleTheEverwiseWatcher watcher = game.getState().getWatcher(RielleTheEverwiseWatcher.class);
	            return watcher != null && watcher.getTimesDiscarded(getControllerId()) == 1;
	        }
			return false;
		}
		


		@Override
		public RielleTheEverwiseTriggeredAbility copy() {
			// TODO Auto-generated method stub
			return new RielleTheEverwiseTriggeredAbility(this);
		}
		
		@Override
	    public String getRule() {
	        return "Whenever you discard one or more cards for the first time each turn, draw that many cards.";
	    }
		
	}

	class RielleTheEverwiseWatcher extends Watcher{

		private final Map<UUID, Integer> timesDiscarded = new HashMap<>();
		
		public RielleTheEverwiseWatcher() {
			super(WatcherScope.GAME);
		}

		@Override
		public void watch(GameEvent event, Game game) {
			if (event.getType() == GameEvent.EventType.DISCARD_CARD) {
	            timesDiscarded.put(event.getPlayerId(), getTimesDiscarded(event.getPlayerId()) + 1);
	        }
		}
		
	    @Override
	    public void reset() {
	        super.reset();
	        timesDiscarded.clear();
	    }
		
		public int getTimesDiscarded(UUID playerId) {
	        return timesDiscarded.getOrDefault(playerId, 0);
	    }
		
	}

}
