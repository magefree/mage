
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GorehornMinotaurs extends CardImpl {

    public GorehornMinotaurs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new BloodthirstAbility(2));
    }

    private GorehornMinotaurs(final GorehornMinotaurs card) {
        super(card);
    }

    @Override
    public GorehornMinotaurs copy() {
        return new GorehornMinotaurs(this);
    }
}
