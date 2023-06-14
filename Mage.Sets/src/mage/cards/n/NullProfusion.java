
package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.PlayCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
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
public final class NullProfusion extends CardImpl {

    public NullProfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // Whenever you play a card, draw a card.
        this.addAbility(new PlayCardTriggeredAbility(TargetController.YOU, Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1)));

        // Your maximum hand size is two.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new MaximumHandSizeControllerEffect(
                        StaticValue.get(2),
                        Duration.WhileOnBattlefield,
                        HandSizeModification.SET,
                        TargetController.YOU
                )
        ));
    }

    private NullProfusion(final NullProfusion card) {
        super(card);
    }

    @Override
    public NullProfusion copy() {
        return new NullProfusion(this);
    }
}