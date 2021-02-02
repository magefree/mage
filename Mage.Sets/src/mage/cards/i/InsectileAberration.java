
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Alvin
 */
public final class InsectileAberration extends CardImpl {

    public InsectileAberration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.INSECT);

        this.color.setBlue(true);
        
        // this card is the second face of double-faced card Delver of Secrets
        this.nightCard = true;

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private InsectileAberration(final InsectileAberration card) {
        super(card);
    }

    @Override
    public InsectileAberration copy() {
        return new InsectileAberration(this);
    }
}
