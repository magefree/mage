package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class PsychicCorrosion extends CardImpl {

    public PsychicCorrosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever you draw a card, each opponent puts the top two cards of their library into their graveyard.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new MillCardsEachPlayerEffect(
                        2, TargetController.OPPONENT
                ), false
        ));
    }

    private PsychicCorrosion(final PsychicCorrosion card) {
        super(card);
    }

    @Override
    public PsychicCorrosion copy() {
        return new PsychicCorrosion(this);
    }
}
