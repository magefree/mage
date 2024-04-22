
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.PlayCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class Recycle extends CardImpl {

    public Recycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}{G}");

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect())); 
        
        // Whenever you play a card, draw a card.
        this.addAbility(new PlayCardTriggeredAbility(TargetController.YOU, Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1)));
        
        // Your maximum hand size is two.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaximumHandSizeControllerEffect(2, Duration.WhileOnBattlefield, HandSizeModification.SET)));
    }

    private Recycle(final Recycle card) {
        super(card);
    }

    @Override
    public Recycle copy() {
        return new Recycle(this);
    }
}