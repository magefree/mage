package mage.cards.p;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author cbt33, Loki (Heartbeat of Spring)
 */
public final class PriceOfGlory extends CardImpl {

    public PriceOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever a player taps a land for mana, if it's not that player's turn, destroy that land.
        this.addAbility(new PriceOfGloryAbility());
    }

    private PriceOfGlory(final PriceOfGlory card) {
        super(card);
    }

    @Override
    public PriceOfGlory copy() {
        return new PriceOfGlory(this);
    }
}

class PriceOfGloryAbility extends TriggeredAbilityImpl {

    private static final String staticText = "Whenever a player taps a land for mana, " +
            "if it's not that player's turn, destroy that land.";

    PriceOfGloryAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
    }

    private PriceOfGloryAbility(final PriceOfGloryAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // it's non mana triggered ability, so ignore it on checking, see TAPPED_FOR_MANA
        if (game.inCheckPlayableState()) {
            return false;
        }
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        if (permanent == null
                || !permanent.isLand(game)
                || game.isActivePlayer(event.getPlayerId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }

    @Override
    public PriceOfGloryAbility copy() {
        return new PriceOfGloryAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}
