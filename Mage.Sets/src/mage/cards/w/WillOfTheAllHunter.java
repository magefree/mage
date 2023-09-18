package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WillOfTheAllHunter extends CardImpl {

    public WillOfTheAllHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 until end of turn. If it's blocking, instead put two +1/+1 counters on it.
        this.getSpellAbility().addEffect(new WillOfTheAllHunterEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private WillOfTheAllHunter(final WillOfTheAllHunter card) {
        super(card);
    }

    @Override
    public WillOfTheAllHunter copy() {
        return new WillOfTheAllHunter(this);
    }
}

class WillOfTheAllHunterEffect extends OneShotEffect {

    WillOfTheAllHunterEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature gets +2/+2 until end of turn. " +
                "If it's blocking, instead put two +1/+1 counters on it.";
    }

    private WillOfTheAllHunterEffect(final WillOfTheAllHunterEffect effect) {
        super(effect);
    }

    @Override
    public WillOfTheAllHunterEffect copy() {
        return new WillOfTheAllHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (permanent.getBlocking() > 0) {
            return permanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
        }
        game.addEffect(new BoostTargetEffect(2, 2), source);
        return true;
    }
}