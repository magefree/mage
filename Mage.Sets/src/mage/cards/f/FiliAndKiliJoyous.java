package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FiliAndKiliJoyous extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Dwarf, Equipment, and Saga spells");

    static {
        filter.add(Predicates.or(
            SubType.DWARF.getPredicate(),
            SubType.EQUIPMENT.getPredicate(),
            SubType.SAGA.getPredicate()
        ));
    }

    public FiliAndKiliJoyous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Add {R}{R}. Spend this mana only to cast Dwarf, Equipment, and Saga spells.
        this.addAbility(new ConditionalColoredManaAbility(Mana.RedMana(2), new ConditionalSpellManaBuilder(filter)));
    }

    private FiliAndKiliJoyous(final FiliAndKiliJoyous card) {
        super(card);
    }

    @Override
    public FiliAndKiliJoyous copy() {
        return new FiliAndKiliJoyous(this);
    }
}
