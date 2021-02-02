

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class GladecoverScout extends CardImpl {

    public GladecoverScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(HexproofAbility.getInstance());
    }

    private GladecoverScout(final GladecoverScout card) {
        super(card);
    }

    @Override
    public GladecoverScout copy() {
        return new GladecoverScout(this);
    }

}
