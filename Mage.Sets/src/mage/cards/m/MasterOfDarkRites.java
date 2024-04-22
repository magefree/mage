package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

/**
 * @author arcox
 */
public final class MasterOfDarkRites extends CardImpl {
    private static final FilterSpell filter = new FilterSpell("Vampire, Cleric, and/or Demon spells");

    static {
        filter.add(Predicates.or(SubType.VAMPIRE.getPredicate(), SubType.CLERIC.getPredicate(), SubType.DEMON.getPredicate()));
    }

    public MasterOfDarkRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}, Sacrifice another creature: Add {B}{B}{B}. Spend this mana only to cast Vampire, Cleric, and/or Demon spells.
        Ability ability = new ConditionalColoredManaAbility(Mana.BlackMana(3), new ConditionalSpellManaBuilder(filter));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private MasterOfDarkRites(final MasterOfDarkRites card) {
        super(card);
    }

    @Override
    public MasterOfDarkRites copy() {
        return new MasterOfDarkRites(this);
    }
}
