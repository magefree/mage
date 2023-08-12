
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;

/**
 * @author L_J
 */
public final class EqualTreatment extends CardImpl {

    public EqualTreatment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // If any source would deal 1 or more damage to a creature or player this turn, it deals 2 damage to that creature or player instead.
        this.getSpellAbility().addEffect(new EqualTreatmentEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private EqualTreatment(final EqualTreatment card) {
        super(card);
    }

    @Override
    public EqualTreatment copy() {
        return new EqualTreatment(this);
    }
}

class EqualTreatmentEffect extends ReplacementEffectImpl {

    public EqualTreatmentEffect() {
        super(Duration.EndOfTurn, Outcome.PreventDamage);
        staticText = "If any source would deal 1 or more damage to a permanent or player this turn, it deals 2 damage to that permanent or player instead";
    }

    public EqualTreatmentEffect(final EqualTreatmentEffect effect) {
        super(effect);
    }

    @Override
    public EqualTreatmentEffect copy() {
        return new EqualTreatmentEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getAmount() > 0;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        damageEvent.setAmount(2);
        return false;
    }
}
