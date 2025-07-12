package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BeastToken2;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BalothPrime extends CardImpl {

    public BalothPrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // This creature enters tapped with six stun counters on it. (If a permanent with a stun counter would become untapped, remove one from it instead.)
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true), "tapped with six stun counters on it. "
                + "<i>(If a permanent with a stun counter would become untapped, remove one from it instead.)</i>"
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.STUN.createInstance(6)));
        this.addAbility(ability);

        // Whenever you sacrifice a land, create a tapped 4/4 green Beast creature token and untap this creature.
        ability = new SacrificePermanentTriggeredAbility(
                new CreateTokenEffect(new BeastToken2(), 1, true), StaticFilters.FILTER_LAND
        );
        ability.addEffect(new UntapSourceEffect().concatBy("and"));
        this.addAbility(ability);

        // {4}, Sacrifice a land: You gain 2 life.
        ability = new SimpleActivatedAbility(new GainLifeEffect(2), new GenericManaCost(4));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_LAND));
        this.addAbility(ability);
    }

    private BalothPrime(final BalothPrime card) {
        super(card);
    }

    @Override
    public BalothPrime copy() {
        return new BalothPrime(this);
    }
}
