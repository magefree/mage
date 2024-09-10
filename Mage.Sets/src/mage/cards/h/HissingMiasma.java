
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.AttackedByCreatureTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author lopho
 */
public final class HissingMiasma extends CardImpl {

    private static final String RULE = "Whenever a creature attacks you, its controller loses 1 life.";
    
    public HissingMiasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");

        // Whenever a creature attacks you, its controller loses 1 life.
        this.addAbility(new AttackedByCreatureTriggeredAbility(new LoseLifeTargetEffect(1).setText("its controller loses 1 life"), false, SetTargetPointer.PLAYER));
    }

    private HissingMiasma(final HissingMiasma card) {
        super(card);
    }

    @Override
    public HissingMiasma copy() {
        return new HissingMiasma(this);
    }
}
