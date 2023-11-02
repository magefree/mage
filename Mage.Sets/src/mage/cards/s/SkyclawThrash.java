
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class SkyclawThrash extends CardImpl {

    public SkyclawThrash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{U}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Skyclaw Thrash attacks, flip a coin. If you win the flip, Skyclaw Thrash gets +1/+1 and gains flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new SkyclawThrashEffect(), false));
    }

    private SkyclawThrash(final SkyclawThrash card) {
        super(card);
    }

    @Override
    public SkyclawThrash copy() {
        return new SkyclawThrash(this);
    }
}

class SkyclawThrashEffect extends OneShotEffect {

    public SkyclawThrashEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip a coin. If you win the flip, {this} gets +1/+1 and gains flying until end of turn";
    }

    private SkyclawThrashEffect(final SkyclawThrashEffect effect) {
        super(effect);
    }

    @Override
    public SkyclawThrashEffect copy() {
        return new SkyclawThrashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (controller.flipCoin(source, game, true) && sourcePermanent != null) {
                ContinuousEffect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                game.addEffect(effect, source);
                effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
