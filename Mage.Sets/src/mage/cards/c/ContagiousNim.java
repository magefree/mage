

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ContagiousNim extends CardImpl {

    public ContagiousNim (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(InfectAbility.getInstance());
    }

    private ContagiousNim(final ContagiousNim card) {
        super(card);
    }

    @Override
    public ContagiousNim copy() {
        return new ContagiousNim(this);
    }

}
