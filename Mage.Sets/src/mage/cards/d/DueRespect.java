
package mage.cards.d;

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
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class DueRespect extends CardImpl {

    public DueRespect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Permanents enter the battlefield tapped this turn.
        this.getSpellAbility().addEffect(new DueRespectEffect());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public DueRespect(final DueRespect card) {
        super(card);
    }

    @Override
    public DueRespect copy() {
        return new DueRespect(this);
    }
}

class DueRespectEffect extends ReplacementEffectImpl {

    DueRespectEffect() {
        super(Duration.EndOfTurn, Outcome.Tap);
        staticText = "Permanents enter the battlefield tapped this turn";
    }

    DueRespectEffect(final DueRespectEffect effect) {
        super(effect);
    }

    @Override
    public DueRespectEffect copy() {
        return new DueRespectEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            permanent.tap(game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}
