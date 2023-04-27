
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Backfir3
 */
public final class DarkwatchElves extends CardImpl {

    public DarkwatchElves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private DarkwatchElves(final DarkwatchElves card) {
        super(card);
    }

    @Override
    public DarkwatchElves copy() {
        return new DarkwatchElves(this);
    }
}
