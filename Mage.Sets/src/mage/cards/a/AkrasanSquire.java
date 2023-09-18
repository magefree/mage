

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class AkrasanSquire extends CardImpl {

    public AkrasanSquire (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new ExaltedAbility());
    }

    private AkrasanSquire(final AkrasanSquire card) {
        super(card);
    }

    @Override
    public AkrasanSquire copy() {
        return new AkrasanSquire(this);
    }

}
