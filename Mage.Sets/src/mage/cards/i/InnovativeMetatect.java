package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.SeekCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

public final class InnovativeMetatect extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonland card with mana value 2 or less");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 2));
    }

    public InnovativeMetatect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever one or more artifact creatures you control deal combat damage to a player, seek a nonland card with mana value 2 or less.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new SeekCardEffect(filter), StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ));
    }

    private InnovativeMetatect(final InnovativeMetatect card) {
        super(card);
    }

    @Override
    public InnovativeMetatect copy() {
        return new InnovativeMetatect(this);
    }
}
