package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CanopyGargantuan extends CardImpl {

    public CanopyGargantuan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // At the beginning of your upkeep, put a number of +1/+1 counters on each other creature you control equal to that creature's toughness.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CanopyGargantuanEffect()));
    }

    private CanopyGargantuan(final CanopyGargantuan card) {
        super(card);
    }

    @Override
    public CanopyGargantuan copy() {
        return new CanopyGargantuan(this);
    }
}

class CanopyGargantuanEffect extends OneShotEffect {

    CanopyGargantuanEffect() {
        super(Outcome.Benefit);
        staticText = "put a number of +1/+1 counters on each other creature you control equal to that creature's toughness";
    }

    private CanopyGargantuanEffect(final CanopyGargantuanEffect effect) {
        super(effect);
    }

    @Override
    public CanopyGargantuanEffect copy() {
        return new CanopyGargantuanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        )) {
            int toughness = permanent.getToughness().getValue();
            if (toughness > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(toughness), source, game);
            }
        }
        return true;
    }
}
