package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RepeatOffender extends CardImpl {

    public RepeatOffender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{B}: If Repeat Offender is suspected, put a +1/+1 counter on it. Otherwise, suspect it.
        this.addAbility(new SimpleActivatedAbility(new RepeatOffenderEffect(), new ManaCostsImpl<>("{2}{B}")));
    }

    private RepeatOffender(final RepeatOffender card) {
        super(card);
    }

    @Override
    public RepeatOffender copy() {
        return new RepeatOffender(this);
    }
}

class RepeatOffenderEffect extends OneShotEffect {

    RepeatOffenderEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is suspected, put a +1/+1 counter on it. Otherwise, suspect it";
    }

    private RepeatOffenderEffect(final RepeatOffenderEffect effect) {
        super(effect);
    }

    @Override
    public RepeatOffenderEffect copy() {
        return new RepeatOffenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        if (permanent.isSuspected()) {
            return permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        permanent.setSuspected(true, game, source);
        return true;
    }
}
