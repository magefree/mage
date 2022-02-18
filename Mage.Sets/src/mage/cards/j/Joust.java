package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Joust extends CardImpl {

    public Joust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Choose target creature you control and target creature you don't control. The creature you control gets +2/+1 until end of turn if it's a Knight. Then those creatures fight each other.
        this.getSpellAbility().addEffect(new JoustEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private Joust(final Joust card) {
        super(card);
    }

    @Override
    public Joust copy() {
        return new Joust(this);
    }
}

class JoustEffect extends OneShotEffect {

    JoustEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target creature you control and target creature you don't control. " +
                "The creature you control gets +2/+1 until end of turn if it's a Knight. " +
                "Then those creatures fight each other. <i>(Each deals damage equal to its power to the other.)</i>";
    }

    private JoustEffect(final JoustEffect effect) {
        super(effect);
    }

    @Override
    public JoustEffect copy() {
        return new JoustEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent creature2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature1 == null) {
            return false;
        }
        if (creature1.hasSubtype(SubType.KNIGHT, game)) {
            ContinuousEffect effect = new BoostTargetEffect(2, 1, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature1.getId(), game));
            game.addEffect(effect, source);
        }
        if (creature2 == null) {
            return true;
        }
        game.getState().processAction(game);
        return creature1.fight(creature2, source, game);
    }
}
