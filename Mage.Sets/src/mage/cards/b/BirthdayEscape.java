package mage.cards.b;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BirthdayEscape extends CardImpl {

    public BirthdayEscape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Draw a card. The Ring tempts you.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
    }

    private BirthdayEscape(final BirthdayEscape card) {
        super(card);
    }

    @Override
    public BirthdayEscape copy() {
        return new BirthdayEscape(this);
    }
}
