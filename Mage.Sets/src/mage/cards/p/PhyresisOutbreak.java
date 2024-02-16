package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class PhyresisOutbreak extends CardImpl {

    public PhyresisOutbreak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Each opponent gets a poison counter. Then each creature your opponents control
        // gets -1/-1 until end of turn for each poison counter its controller has.
        this.getSpellAbility().addEffect(new AddCountersPlayersEffect(
                CounterType.POISON.createInstance(), TargetController.OPPONENT
        ));
        this.getSpellAbility().addEffect(new PhyresisOutbreakEffect().concatBy("Then"));
    }

    private PhyresisOutbreak(final PhyresisOutbreak card) {
        super(card);
    }

    @Override
    public PhyresisOutbreak copy() {
        return new PhyresisOutbreak(this);
    }
}

class PhyresisOutbreakEffect extends OneShotEffect {

    PhyresisOutbreakEffect() {
        super(Outcome.UnboostCreature);
        staticText = "each creature your opponents control gets -1/-1 until " +
                "end of turn for each poison counter its controller has";
    }

    private PhyresisOutbreakEffect(final PhyresisOutbreakEffect effect) {
        super(effect);
    }

    @Override
    public PhyresisOutbreakEffect copy() {
        return new PhyresisOutbreakEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId(), true)) {
            int totalPoison = game.getPlayer(opponentId).getCounters().getCount(CounterType.POISON);
            BoostTargetEffect effect = new BoostTargetEffect(totalPoison * -1, totalPoison * -1, Duration.EndOfTurn);
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_A_CREATURE, opponentId, game)) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
