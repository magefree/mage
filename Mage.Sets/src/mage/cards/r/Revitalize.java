package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class Revitalize extends CardImpl {

    public Revitalize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // You gain 3 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(3));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Revitalize(final Revitalize card) {
        super(card);
    }

    @Override
    public Revitalize copy() {
        return new Revitalize(this);
    }
}
