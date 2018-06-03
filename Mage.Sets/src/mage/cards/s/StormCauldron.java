
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TapLandForManaAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Quercitron
 */
public final class StormCauldron extends CardImpl {

    public StormCauldron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Each player may play an additional land during each of their turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsAllEffect()));

        // Whenever a land is tapped for mana, return it to its owner's hand.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return it to its owner's hand");
        this.addAbility(new TapLandForManaAllTriggeredAbility(effect, false, true));
    }

    public StormCauldron(final StormCauldron card) {
        super(card);
    }

    @Override
    public StormCauldron copy() {
        return new StormCauldron(this);
    }
}
