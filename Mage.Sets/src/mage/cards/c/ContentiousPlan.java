package mage.cards.c;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author antoni-g
 */
public final class ContentiousPlan extends CardImpl {

    public ContentiousPlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        this.getSpellAbility().addEffect(new ProliferateEffect());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ContentiousPlan(final ContentiousPlan card) {
        super(card);
    }

    @Override
    public ContentiousPlan copy() {
        return new ContentiousPlan(this);
    }
}
