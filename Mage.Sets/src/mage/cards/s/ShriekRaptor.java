
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ShriekRaptor extends CardImpl {

    public ShriekRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
    }

    private ShriekRaptor(final ShriekRaptor card) {
        super(card);
    }

    @Override
    public ShriekRaptor copy() {
        return new ShriekRaptor(this);
    }
}
