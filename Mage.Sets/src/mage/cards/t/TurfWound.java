
package mage.cards.t;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
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
public final class TurfWound extends CardImpl {

    public TurfWound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Target player can't play land cards this turn.
        this.getSpellAbility().addEffect(new TurfWoundEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private TurfWound(final TurfWound card) {
        super(card);
    }

    @Override
    public TurfWound copy() {
        return new TurfWound(this);
    }
}

class TurfWoundEffect extends ContinuousRuleModifyingEffectImpl {

    public TurfWoundEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Target player can't play land cards this turn";
    }

    public TurfWoundEffect(final TurfWoundEffect effect) {
        super(effect);
    }

    @Override
    public TurfWoundEffect copy() {
        return new TurfWoundEffect(this);
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
