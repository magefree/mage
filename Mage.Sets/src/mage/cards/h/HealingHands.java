
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Wehk
 */
public final class HealingHands extends CardImpl {

    public HealingHands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Target player gains 4 life.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private HealingHands(final HealingHands card) {
        super(card);
    }

    @Override
    public HealingHands copy() {
        return new HealingHands(this);
    }
}
