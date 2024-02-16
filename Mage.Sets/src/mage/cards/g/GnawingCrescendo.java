package mage.cards.g;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnawingCrescendo extends CardImpl {

    public GnawingCrescendo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Creatures you control get +2/+0 until end of turn. Whenever a nontoken creature you control dies this turn, create a 1/1 black Rat creature token with "This creature can't block."
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new GnawingCrescendoTriggeredAbility()));
    }

    private GnawingCrescendo(final GnawingCrescendo card) {
        super(card);
    }

    @Override
    public GnawingCrescendo copy() {
        return new GnawingCrescendo(this);
    }
}

class GnawingCrescendoTriggeredAbility extends DelayedTriggeredAbility {

    GnawingCrescendoTriggeredAbility() {
        super(new CreateTokenEffect(new RatCantBlockToken()), Duration.EndOfTurn, false, false);
        setTriggerPhrase("Whenever a nontoken creature you control dies this turn, ");
    }

    private GnawingCrescendoTriggeredAbility(final GnawingCrescendoTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GnawingCrescendoTriggeredAbility copy() {
        return new GnawingCrescendoTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent() && zEvent.getTarget() != null
                && zEvent.getTarget().isControlledBy(getControllerId())
                && zEvent.getTarget().isCreature(game)
                && !(zEvent.getTarget() instanceof PermanentToken);
    }
}
