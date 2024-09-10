

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class Blightwidow extends CardImpl {

    public Blightwidow (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
    }

    private Blightwidow(final Blightwidow card) {
        super(card);
    }

    @Override
    public Blightwidow copy() {
        return new Blightwidow(this);
    }

}
