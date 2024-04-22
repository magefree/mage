

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */


public final class MurmuringPhantasm extends CardImpl {

    public MurmuringPhantasm (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

    }

    private MurmuringPhantasm(final MurmuringPhantasm card) {
        super(card);
    }

    @Override
    public MurmuringPhantasm copy() {
        return new MurmuringPhantasm(this);
    }

}
