
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ThornwealdArcher extends CardImpl {

    public ThornwealdArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(ReachAbility.getInstance());
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private ThornwealdArcher(final ThornwealdArcher card) {
        super(card);
    }

    @Override
    public ThornwealdArcher copy() {
        return new ThornwealdArcher(this);
    }
}
