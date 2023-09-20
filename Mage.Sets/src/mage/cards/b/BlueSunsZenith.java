

package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class BlueSunsZenith extends CardImpl {

    public BlueSunsZenith (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}{U}{U}");

        // Target player draws X cards. Shuffle Blue Sun's Zenith into its owner's library.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private BlueSunsZenith(final BlueSunsZenith card) {
        super(card);
    }

    @Override
    public BlueSunsZenith copy() {
        return new BlueSunsZenith(this);
    }

}
