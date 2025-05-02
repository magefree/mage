package mage.cards.h;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HeightenedAwareness extends CardImpl {

    public HeightenedAwareness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // As Heightened Awareness enters the battlefield, discard your hand.
        this.addAbility(new AsEntersBattlefieldAbility(new DiscardHandControllerEffect()));

        // At the beginning of your draw step, draw an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DrawCardSourceControllerEffect(1)
                .setText("draw an additional card"),
                false));
    }

    private HeightenedAwareness(final HeightenedAwareness card) {
        super(card);
    }

    @Override
    public HeightenedAwareness copy() {
        return new HeightenedAwareness(this);
    }
}
