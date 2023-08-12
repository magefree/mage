
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
 * @author LevelX2
 */
public final class TimeStretch extends CardImpl {

    public TimeStretch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{8}{U}{U}");


        // Target player takes two extra turns after this one.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TimeStretchEffect());

    }

    private TimeStretch(final TimeStretch card) {
        super(card);
    }

    @Override
    public TimeStretch copy() {
        return new TimeStretch(this);
    }
}

class TimeStretchEffect extends OneShotEffect {

    public TimeStretchEffect() {
        super(Outcome.ExtraTurn);
        staticText = "Target player takes two extra turns after this one";
    }

    public TimeStretchEffect(final TimeStretchEffect effect) {
        super(effect);
    }

    @Override
    public TimeStretchEffect copy() {
        return new TimeStretchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getFirstTarget() == null) {
            return false;
        }

        game.getState().getTurnMods().add(new TurnMod(source.getFirstTarget()).withExtraTurn());
        game.getState().getTurnMods().add(new TurnMod(source.getFirstTarget()).withExtraTurn());
        return true;
    }
}
