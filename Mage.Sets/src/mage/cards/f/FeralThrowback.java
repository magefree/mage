
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.abilities.keyword.ProvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class FeralThrowback extends CardImpl {

    public FeralThrowback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Amplify 2
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify2));
        // Provoke
        this.addAbility(new ProvokeAbility());
    }

    private FeralThrowback(final FeralThrowback card) {
        super(card);
    }

    @Override
    public FeralThrowback copy() {
        return new FeralThrowback(this);
    }
}
