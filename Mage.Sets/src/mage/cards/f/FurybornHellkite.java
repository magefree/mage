
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class FurybornHellkite extends CardImpl {

    public FurybornHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(new BloodthirstAbility(6));
        this.addAbility(FlyingAbility.getInstance());
    }

    private FurybornHellkite(final FurybornHellkite card) {
        super(card);
    }

    @Override
    public FurybornHellkite copy() {
        return new FurybornHellkite(this);
    }
}
