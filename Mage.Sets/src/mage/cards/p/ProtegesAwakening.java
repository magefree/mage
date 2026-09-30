package mage.cards.p;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ProtegesAwakening extends CardImpl {

    public ProtegesAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Empower Jace 6.
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(6));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ProtegesAwakening(final ProtegesAwakening card) {
        super(card);
    }

    @Override
    public ProtegesAwakening copy() {
        return new ProtegesAwakening(this);
    }
}
