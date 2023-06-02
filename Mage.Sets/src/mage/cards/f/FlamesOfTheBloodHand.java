
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class FlamesOfTheBloodHand extends CardImpl {

    public FlamesOfTheBloodHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Flames of the Blood Hand deals 4 damage to target player. The damage can't be prevented.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4, false));
        // If that player would gain life this turn, that player gains no life instead.
        this.getSpellAbility().addEffect(new FlamesOfTheBloodHandReplacementEffect());
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    private FlamesOfTheBloodHand(final FlamesOfTheBloodHand card) {
        super(card);
    }

    @Override
    public FlamesOfTheBloodHand copy() {
        return new FlamesOfTheBloodHand(this);
    }
}

class FlamesOfTheBloodHandReplacementEffect extends ReplacementEffectImpl {

    public FlamesOfTheBloodHandReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "If that player or that planeswalker's controller would gain life this turn, that player gains no life instead";
    }

    public FlamesOfTheBloodHandReplacementEffect(final FlamesOfTheBloodHandReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FlamesOfTheBloodHandReplacementEffect copy() {
        return new FlamesOfTheBloodHandReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

}
