
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class GloweringRogon extends CardImpl {

    public GloweringRogon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Amplify 1
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify1));
    }

    private GloweringRogon(final GloweringRogon card) {
        super(card);
    }

    @Override
    public GloweringRogon copy() {
        return new GloweringRogon(this);
    }
}
