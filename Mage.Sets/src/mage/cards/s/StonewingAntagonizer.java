
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class StonewingAntagonizer extends CardImpl {

    public StonewingAntagonizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"");
        this.subtype.add(SubType.GARGOYLE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private StonewingAntagonizer(final StonewingAntagonizer card) {
        super(card);
    }

    @Override
    public StonewingAntagonizer copy() {
        return new StonewingAntagonizer(this);
    }
}
