package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArwenMortalQueen extends CardImpl {

    public ArwenMortalQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Arwen, Mortal Queen enters the battlefield with an indestructible counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance(1)),
                "with an indestructible counter on it"
        ));

        // {1}, Remove an indestructible counter from Arwen: Another target creature gains indestructible until end of turn. Put a +1/+1 counter and a lifelink counter on that creature and a +1/+1 counter and a lifelink counter on Arwen.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance()),
                new RemoveCountersSourceCost(CounterType.INDESTRUCTIBLE.createInstance())
        );
        ability.addManaCost(new GenericManaCost(1));
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("Put a +1/+1 counter"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.LIFELINK.createInstance())
                .setText("and a lifelink counter on that creature"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("and a +1/+1 counter"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.LIFELINK.createInstance())
                .setText("and a lifelink counter on {this}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private ArwenMortalQueen(final ArwenMortalQueen card) {
        super(card);
    }

    @Override
    public ArwenMortalQueen copy() {
        return new ArwenMortalQueen(this);
    }
}
