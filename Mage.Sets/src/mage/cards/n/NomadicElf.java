
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class NomadicElf extends CardImpl {

    public NomadicElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOMAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new ManaCostsImpl<>("{1}{G}")));
    }

    private NomadicElf(final NomadicElf card) {
        super(card);
    }

    @Override
    public NomadicElf copy() {
        return new NomadicElf(this);
    }
}
