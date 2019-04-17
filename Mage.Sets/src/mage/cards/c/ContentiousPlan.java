package mage.cards.c;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;

/**
 *
 * @author antoni-g
 */
public final class ContentiousPlan extends CardImpl {

    public ContentiousPlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");
        
        // Proliferate.
        this.getSpellAbility().addEffect(new ProliferateEffect());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private ContentiousPlan(final ContentiousPlan card) {
        super(card);
    }

    @Override
    public ContentiousPlan copy() {
        return new ContentiousPlan(this);
    }
}
