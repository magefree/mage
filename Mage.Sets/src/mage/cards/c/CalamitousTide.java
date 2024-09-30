package mage.cards.c;

import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CalamitousTide extends CardImpl {

    public CalamitousTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return up to two target creatures to their owners' hands. Draw two cards, then discard a card.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
    }

    private CalamitousTide(final CalamitousTide card) {
        super(card);
    }

    @Override
    public CalamitousTide copy() {
        return new CalamitousTide(this);
    }
}
