
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author fireshoes
 */
public final class OrmendahlProfanePrince extends CardImpl {

    public OrmendahlProfanePrince(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(9);
        this.toughness = new MageInt(7);
        this.color.setBlack(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private OrmendahlProfanePrince(final OrmendahlProfanePrince card) {
        super(card);
    }

    @Override
    public OrmendahlProfanePrince copy() {
        return new OrmendahlProfanePrince(this);
    }
}
