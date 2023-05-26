package mage.cards.a;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class AllWillBeOne extends CardImpl {

    public AllWillBeOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Whenever you put one or more counters on a permanent or player, All Will Be One deals that much
        // damage to target opponent, creature an opponent controls, or planeswalker an opponent controls.
        this.addAbility(new AllWillBeOneTriggeredAbility());
    }

    private AllWillBeOne(final AllWillBeOne card) {
        super(card);
    }

    @Override
    public AllWillBeOne copy() {
        return new AllWillBeOne(this);
    }
}

class AllWillBeOneTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanentOrPlayer filter
            = new FilterCreaturePlayerOrPlaneswalker("opponent, creature an opponent controls, or planeswalker an opponent controls.");

    static {
        filter.getPermanentFilter().add(TargetController.NOT_YOU.getControllerPredicate());
        filter.getPlayerFilter().add(TargetController.OPPONENT.getPlayerPredicate());
    }

    AllWillBeOneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(SavedDamageValue.MUCH));
        this.addTarget(new TargetPermanentOrPlayer(filter));
    }

    private AllWillBeOneTriggeredAbility(final AllWillBeOneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        getEffects().setValue("damage", event.getAmount());
        return true;
    }

    @Override
    public AllWillBeOneTriggeredAbility copy() {
        return new AllWillBeOneTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you put one or more counters on a permanent or player, {this} deals that much damage " +
                "to target opponent, creature an opponent controls, or planeswalker an opponent controls.";
    }
}
