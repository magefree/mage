
package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LoneFox
 */
public final class ResearchTheDeep extends CardImpl {

    public ResearchTheDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        // Clash with an opponent. If you win, return Research the Deep to its owner's hand.
        this.getSpellAbility().addEffect(new DoIfClashWonEffect(ReturnToHandSpellEffect.getInstance()));
    }

    private ResearchTheDeep(final ResearchTheDeep card) {
        super(card);
    }

    @Override
    public ResearchTheDeep copy() {
        return new ResearchTheDeep(this);
    }
}
