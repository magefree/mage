
package mage.cards.v;

import java.util.UUID;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class ValorInAkros extends CardImpl {

    public ValorInAkros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // Whenever a creature enters the battlefield under your control, creatures you control get +1/+1 until end of turn.
        this.addAbility(new CreatureEntersBattlefieldTriggeredAbility(new BoostControlledEffect(1,1,Duration.EndOfTurn)));        
    }

    public ValorInAkros(final ValorInAkros card) {
        super(card);
    }

    @Override
    public ValorInAkros copy() {
        return new ValorInAkros(this);
    }
}
