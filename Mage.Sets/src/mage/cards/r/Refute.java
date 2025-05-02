package mage.cards.r;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Refute extends CardImpl {

    public Refute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell. Draw a card, then discard a card.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(1, 1));
    }

    private Refute(final Refute card) {
        super(card);
    }

    @Override
    public Refute copy() {
        return new Refute(this);
    }
}
