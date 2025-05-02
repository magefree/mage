

package mage.cards.p;

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
public final class PhyrexianDigester extends CardImpl {

    public PhyrexianDigester (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(InfectAbility.getInstance());
    }

    private PhyrexianDigester(final PhyrexianDigester card) {
        super(card);
    }

    @Override
    public PhyrexianDigester copy() {
        return new PhyrexianDigester(this);
    }

}
