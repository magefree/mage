
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ServantOfNefarox extends CardImpl {

    public ServantOfNefarox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Exalted
        this.addAbility(new ExaltedAbility());
    }

    private ServantOfNefarox(final ServantOfNefarox card) {
        super(card);
    }

    @Override
    public ServantOfNefarox copy() {
        return new ServantOfNefarox(this);
    }
}
