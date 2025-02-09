package mage.abilities.costs.common;

import java.util.Locale;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.MageObjectReference;
import mage.players.Player;


/**
 * @author padfoot
 */
public class ExileSourceWithTimeCountersCost extends CostImpl {

    private final int counters;
    private final boolean checksSuspend;
    private final boolean givesSuspend;
    private final Zone fromZone;

    public ExileSourceWithTimeCountersCost(int counters) {
    	this (counters, true, false, null);
    }

    public ExileSourceWithTimeCountersCost(int counters, boolean givesSuspend, boolean checksSuspend, Zone fromZone) {
	this.counters = counters;
        this.givesSuspend = givesSuspend;	
	this.checksSuspend = checksSuspend;
	this.fromZone = fromZone;
        this.text = "exile {this} " + 
		((fromZone != null) ? " from your " + fromZone.toString().toLowerCase(Locale.ENGLISH) : "") + 
		" and put " + counters + " time counters on it" + 
		(givesSuspend ? ". It gains suspend" : "") + 
		(checksSuspend ? ". If it doesn't have suspend, it gains suspend" : "");
    }

   private ExileSourceWithTimeCountersCost(final ExileSourceWithTimeCountersCost cost) {
        super(cost);
	this.counters = cost.counters;
        this.givesSuspend = cost.givesSuspend;	
	this.checksSuspend = cost.checksSuspend;
	this.fromZone = cost.fromZone;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
	if (controller == null) {
            return paid;
        }
        Card card = game.getCard(source.getSourceId());
	boolean hasSuspend = card.getAbilities(game).containsClass(SuspendAbility.class);
        if (card != null && (fromZone == null || fromZone == game.getState().getZone(source.getSourceId()))) {
            UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
            if (controller.moveCardsToExile(card, source, game, true, exileId, "Suspended cards of " + controller.getName())) {
                card.addCounters(CounterType.TIME.createInstance(counters), controller.getId(), source, game);
	    game.informPlayers(controller.getLogName() + " exiles " + card.getLogName() + ((fromZone != null) ? " from their " + fromZone.toString().toLowerCase(Locale.ENGLISH) : "") + " with " + counters + " time counters on it.");
	    if (givesSuspend || (checksSuspend && !hasSuspend)) {
	        game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);
	    }
	}
        // 117.11. The actions performed when paying a cost may be modified by effects.
        // Even if they are, meaning the actions that are performed don't match the actions
        // that are called for, the cost has still been paid.
        // so return state here is not important because the user indended to exile the target anyway
        paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return (game.getCard(source.getSourceId()) != null && (fromZone == null || fromZone == game.getState().getZone(source.getSourceId())));
    }

    @Override
    public ExileSourceWithTimeCountersCost copy() {
        return new ExileSourceWithTimeCountersCost(this);
    }
}
