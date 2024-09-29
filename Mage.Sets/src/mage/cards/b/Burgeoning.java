package mage.cards.b;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author duncant
 */
public final class Burgeoning extends CardImpl {

    public Burgeoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Whenever an opponent plays a land, you may put a land card from your hand onto the battlefield.
        this.addAbility(new BurgeoningTriggeredAbility());
    }

    private Burgeoning(final Burgeoning card) {
        super(card);
    }

    @Override
    public Burgeoning copy() {
        return new Burgeoning(this);
    }
}

class BurgeoningTriggeredAbility extends TriggeredAbilityImpl {

    BurgeoningTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A));
    }

    private BurgeoningTriggeredAbility(final BurgeoningTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        return land != null && game.getOpponents(controllerId).contains(land.getControllerId());
    }

    @Override
    public BurgeoningTriggeredAbility copy() {
        return new BurgeoningTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent plays a land, you may put a land card from your hand onto the battlefield.";
    }
}
