

package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class TimeWarp extends CardImpl {

    public TimeWarp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TimeWarpEffect());
    }

    private TimeWarp(final TimeWarp card) {
        super(card);
    }

    @Override
    public TimeWarp copy() {
        return new TimeWarp(this);
    }

}

class TimeWarpEffect extends OneShotEffect {

    public TimeWarpEffect() {
        super(Outcome.ExtraTurn);
        staticText = "Target player takes an extra turn after this one";
    }

    public TimeWarpEffect(final TimeWarpEffect effect) {
        super(effect);
    }

    @Override
    public TimeWarpEffect copy() {
        return new TimeWarpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getFirstTarget() == null) {
            return false;
        }
        game.getState().getTurnMods().add(new TurnMod(source.getFirstTarget()).withExtraTurn());
        return true;
    }
}