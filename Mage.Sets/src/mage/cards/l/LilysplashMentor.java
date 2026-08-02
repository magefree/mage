package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class LilysplashMentor extends CardImpl {

    public LilysplashMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {1}{G}{U}: Exile another target creature you control, then return it to the battlefield under its owner's control with a +1/+1 counter on it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new ExileThenReturnTargetEffect(false, false).withEnterWithCounters(CounterType.P1P1.createInstance()),
            new ManaCostsImpl<>("{1}{G}{U}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private LilysplashMentor(final LilysplashMentor card) {
        super(card);
    }

    @Override
    public LilysplashMentor copy() {
        return new LilysplashMentor(this);
    }
}
