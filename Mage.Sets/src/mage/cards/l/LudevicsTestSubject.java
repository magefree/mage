
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class LudevicsTestSubject extends CardImpl {

    public LudevicsTestSubject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.LIZARD, SubType.EGG);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = LudevicsAbomination.class;

        this.addAbility(DefenderAbility.getInstance());
        // {1}{U}: Put a hatchling counter on Ludevic's Test Subject. Then if there are five or more hatchling counters on it, remove all of them and transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.HATCHLING.createInstance()), new ManaCostsImpl<>("{1}{U}"));
        ability.addEffect(new LudevicsTestSubjectEffect());
        this.addAbility(ability);
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
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            if (p.getCounters(game).getCount(CounterType.HATCHLING) >= 5) {
                p.removeCounters(CounterType.HATCHLING.getName(), p.getCounters(game).getCount(CounterType.HATCHLING), source, game);
                TransformSourceEffect effect = new TransformSourceEffect();
                return effect.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public LudevicsTestSubjectEffect copy() {
        return new LudevicsTestSubjectEffect(this);
    }
}
