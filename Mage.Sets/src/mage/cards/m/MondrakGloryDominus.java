package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.replacement.CreateTwiceThatManyTokensEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Grath
 */
public final class MondrakGloryDominus extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("other artifacts and/or creatures");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public MondrakGloryDominus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // If an effect would create one or more tokens under your control, it creates twice that many of those tokens instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CreateTwiceThatManyTokensEffect()));

        // {1}{W/P}{W/P}, Sacrifice two other artifacts and/or creatures: Put an indestructible counter on Mondrak, Glory Dominus.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(
                        CounterType.INDESTRUCTIBLE.createInstance()
                ),
                new ManaCostsImpl<>("{1}{W/P}{W/P}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, filter)));
        this.addAbility(ability);
    }

    private MondrakGloryDominus(final MondrakGloryDominus card) {
        super(card);
    }

    @Override
    public MondrakGloryDominus copy() {
        return new MondrakGloryDominus(this);
    }
}
