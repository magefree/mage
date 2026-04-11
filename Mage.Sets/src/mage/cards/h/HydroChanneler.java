package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HydroChanneler extends CardImpl {

    public HydroChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add {U}. Spend this mana only to cast an instant or sorcery spell.
        this.addAbility(new ConditionalColoredManaAbility(Mana.BlueMana(1), new InstantOrSorcerySpellManaBuilder()));

        // {1}, {T}: Add one mana of any color. Spend this mana only to cast an instant or sorcery spell.
        Ability ability = new ConditionalAnyColorManaAbility(new ManaCostsImpl<>("{1}"), 1, new InstantOrSorcerySpellManaBuilder());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HydroChanneler(final HydroChanneler card) {
        super(card);
    }

    @Override
    public HydroChanneler copy() {
        return new HydroChanneler(this);
    }
}
