
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureSpell;

/**
 *
 * @author fireshoes
 */
public final class BeastcallerSavant extends CardImpl {

    public BeastcallerSavant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF, SubType.SHAMAN, SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new ConditionalSpellManaBuilder(new FilterCreatureSpell("a creature spell"))));
    }

    private BeastcallerSavant(final BeastcallerSavant card) {
        super(card);
    }

    @Override
    public BeastcallerSavant copy() {
        return new BeastcallerSavant(this);
    }
}
