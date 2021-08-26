

package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author  ciaccona007
 */
public final class GrowthSpurt extends CardImpl {

    public GrowthSpurt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GrowthSpurtEffect());
    }

    private GrowthSpurt(final GrowthSpurt card) {
        super(card);
    }

    @Override
    public GrowthSpurt copy() {
        return new GrowthSpurt(this);
    }
}

class GrowthSpurtEffect extends OneShotEffect {
    GrowthSpurtEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Roll a six-sided die. Target creature gets +X/+X until end of turn, where X is the result";
    }

    GrowthSpurtEffect(final GrowthSpurtEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int result = controller.rollDice(outcome, source, game, 6);
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                ContinuousEffect effect = new BoostTargetEffect(result, result, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }
        return false;
    }

    public GrowthSpurtEffect copy() {
        return new GrowthSpurtEffect(this);
    }
}
