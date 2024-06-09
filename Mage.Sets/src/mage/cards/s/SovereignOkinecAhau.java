package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class SovereignOkinecAhau extends CardImpl {

    public SovereignOkinecAhau(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Whenever Sovereign Okinec Ahau attacks, for each creature you control with power greater than that
        // creature's base power, put a number of +1/+1 counters on that creature equal to the difference.
        this.addAbility(new AttacksTriggeredAbility(new SovereignOkinecAhauEffect()));
    }

    private SovereignOkinecAhau(final SovereignOkinecAhau card) {
        super(card);
    }

    @Override
    public SovereignOkinecAhau copy() {
        return new SovereignOkinecAhau(this);
    }
}


class SovereignOkinecAhauEffect extends OneShotEffect {

    SovereignOkinecAhauEffect() {
        super(Outcome.Benefit);
        staticText = "for each creature you control with power greater than that " +
                "creature's base power, put a number of +1/+1 counters on that creature equal to the difference.";
    }

    private SovereignOkinecAhauEffect(final SovereignOkinecAhauEffect effect) {
        super(effect);
    }

    @Override
    public SovereignOkinecAhauEffect copy() {
        return new SovereignOkinecAhauEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game)) {
            int powerDiff = permanent.getPower().getValue() - permanent.getPower().getModifiedBaseValue();
            if (powerDiff > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(powerDiff), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
