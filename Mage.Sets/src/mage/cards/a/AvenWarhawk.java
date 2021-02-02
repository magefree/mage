
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class AvenWarhawk extends CardImpl {

    public AvenWarhawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Amplify 1
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify1));
        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private AvenWarhawk(final AvenWarhawk card) {
        super(card);
    }

    @Override
    public AvenWarhawk copy() {
        return new AvenWarhawk(this);
    }
}
