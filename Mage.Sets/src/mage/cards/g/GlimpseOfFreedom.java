package mage.cards.g;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlimpseOfFreedom extends CardImpl {

    public GlimpseOfFreedom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Escape — {2}{U}, Exile five other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{2}{U}", 5));
    }

    private GlimpseOfFreedom(final GlimpseOfFreedom card) {
        super(card);
    }

    @Override
    public GlimpseOfFreedom copy() {
        return new GlimpseOfFreedom(this);
    }
}
