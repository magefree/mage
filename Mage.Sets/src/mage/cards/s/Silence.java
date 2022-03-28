
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Silence extends CardImpl {

    public Silence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Your opponents can't cast spells this turn. (Spells cast before this resolves are unaffected.)
        this.getSpellAbility().addEffect(new SilenceEffect());
    }

    private Silence(final Silence card) {
        super(card);
    }

    @Override
    public Silence copy() {
        return new Silence(this);
    }
}

class SilenceEffect extends ContinuousRuleModifyingEffectImpl {

    public SilenceEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Your opponents can't cast spells this turn. <i>(Spells cast before this resolves are unaffected.)</i>";
    }

    public SilenceEffect(final SilenceEffect effect) {
        super(effect);
    }

    @Override
    public SilenceEffect copy() {
        return new SilenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getOpponents(source.getControllerId()).contains(event.getPlayerId());
    }

}
