package mage.cards.r;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RemoveUpToAmountCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RenderInert extends CardImpl {

    public RenderInert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Remove up to five counters from target permanent.
        this.getSpellAbility().addEffect(new RemoveUpToAmountCountersEffect(5));
        this.getSpellAbility().addTarget(new TargetPermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private RenderInert(final RenderInert card) {
        super(card);
    }

    @Override
    public RenderInert copy() {
        return new RenderInert(this);
    }
}