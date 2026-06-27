package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.common.GetPoisonCountersCost;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheSerpentSociety extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control with deathtouch");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new AbilityPredicate(DeathtouchAbility.class));
    }

    public TheSerpentSociety(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Ward--Get five poison counters.
        this.addAbility(new WardAbility(new GetPoisonCountersCost(5), false));

        // Whenever another creature you control with deathtouch dies, each opponent sacrifices a nontoken creature of their choice.
        Ability ability = new DiesCreatureTriggeredAbility(
            new SacrificeOpponentsEffect(StaticFilters.FILTER_CREATURE_NON_TOKEN), false, filter
        );
        this.addAbility(ability);
    }

    private TheSerpentSociety(final TheSerpentSociety card) {
        super(card);
    }

    @Override
    public TheSerpentSociety copy() {
        return new TheSerpentSociety(this);
    }
}
