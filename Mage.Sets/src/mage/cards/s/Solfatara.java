
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class Solfatara extends CardImpl {

    public Solfatara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Target player can't play land cards this turn.
        this.getSpellAbility().addEffect(new SolfataraEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse), false));
    }

    private Solfatara(final Solfatara card) {
        super(card);
    }

    @Override
    public Solfatara copy() {
        return new Solfatara(this);
    }
}

class SolfataraEffect extends ContinuousRuleModifyingEffectImpl {

    public SolfataraEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Target player can't play land cards this turn";
    }

    public SolfataraEffect(final SolfataraEffect effect) {
        super(effect);
    }

    @Override
    public SolfataraEffect copy() {
        return new SolfataraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't play lands this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.PLAY_LAND && event.getPlayerId().equals(source.getFirstTarget())) {
            return true;
        }
        return false;
    }

}
