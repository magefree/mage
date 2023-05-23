package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LudevicsTestSubject extends TransformingDoubleFacedCard {

    public LudevicsTestSubject(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.LIZARD, SubType.EGG}, "{1}{U}",
                "Ludevic's Abomination",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.LIZARD, SubType.HORROR}, "U"
        );
        this.getLeftHalfCard().setPT(0, 3);
        this.getRightHalfCard().setPT(13, 13);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // {1}{U}: Put a hatchling counter on Ludevic's Test Subject. Then if there are five or more hatchling counters on it, remove all of them and transform it.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.HATCHLING.createInstance()), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addEffect(new LudevicsTestSubjectEffect());
        this.getLeftHalfCard().addAbility(ability);

        // Ludevic's Abomination
        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());
    }

    private LudevicsTestSubject(final LudevicsTestSubject card) {
        super(card);
    }

    @Override
    public LudevicsTestSubject copy() {
        return new LudevicsTestSubject(this);
    }
}

class LudevicsTestSubjectEffect extends OneShotEffect {

    LudevicsTestSubjectEffect() {
        super(Outcome.Benefit);
        staticText = "Then if there are five or more hatchling counters on it, remove all of them and transform it";
    }

    LudevicsTestSubjectEffect(final LudevicsTestSubjectEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        int count = permanent.getCounters(game).getCount(CounterType.HATCHLING);
        if (count < 5) {
            return false;
        }
        permanent.removeCounters(CounterType.HATCHLING.getName(), count, source, game);
        permanent.transform(source, game);
        return true;
    }

    @Override
    public LudevicsTestSubjectEffect copy() {
        return new LudevicsTestSubjectEffect(this);
    }
}
