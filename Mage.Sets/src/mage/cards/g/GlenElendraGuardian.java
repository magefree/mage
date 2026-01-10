package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardTargetControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GlenElendraGuardian extends CardImpl {

    public GlenElendraGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature enters with a -1/-1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
            new AddCountersSourceEffect(CounterType.M1M1.createInstance(1)),
            "with a -1/-1 counter on it"
        ));

        // {1}{U}, Remove a counter from this creature: Counter target noncreature spell. Its controller draws a card.
        Ability ability = new SimpleActivatedAbility(new CounterTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new RemoveCountersSourceCost(1));
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        ability.addEffect(new DrawCardTargetControllerEffect(1));
        this.addAbility(ability);
    }

    private GlenElendraGuardian(final GlenElendraGuardian card) {
        super(card);
    }

    @Override
    public GlenElendraGuardian copy() {
        return new GlenElendraGuardian(this);
    }
}
