
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class ZombieBrute extends CardImpl {

    public ZombieBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Amplify 1
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify1));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private ZombieBrute(final ZombieBrute card) {
        super(card);
    }

    @Override
    public ZombieBrute copy() {
        return new ZombieBrute(this);
    }
}
