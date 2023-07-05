package mage.cards.b;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Blur extends CardImpl {

    public Blur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control.
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, true));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Blur(final Blur card) {
        super(card);
    }

    @Override
    public Blur copy() {
        return new Blur(this);
    }
}
