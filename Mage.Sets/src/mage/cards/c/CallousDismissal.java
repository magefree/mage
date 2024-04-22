package mage.cards.c;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallousDismissal extends CardImpl {

    public CallousDismissal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Amass 1.
        this.getSpellAbility().addEffect(new AmassEffect(1, SubType.ZOMBIE).concatBy("<br>"));
    }

    private CallousDismissal(final CallousDismissal card) {
        super(card);
    }

    @Override
    public CallousDismissal copy() {
        return new CallousDismissal(this);
    }
}
