package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_ANOTHER_TARGET_CREATURE;

/**
 *
 * @author notshauna
 */

public final class LynaVeilofVengeance extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with shadow");

    static {
        filter.add(new AbilityPredicate(ShadowAbility.class));
    }

    public LynaVeilofVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE},"{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SOLTARI);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // Whenever a creature you control with shadow deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter,
                false, SetTargetPointer.PERMANENT, true
        ));

        // {3}: Put a shadow counter on another target creature. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.SHADOW.createInstance()),
                new ManaCostsImpl<>("{3}"));
        ability.addTarget(new TargetPermanent(FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private LynaVeilofVengeance(final LynaVeilofVengeance card) {
        super(card);
    }

    @Override
    public LynaVeilofVengeance copy() {
        return new LynaVeilofVengeance(this);
    }
}