package mage.cards.c;

import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ContingencyPlan extends CardImpl {

    public ContingencyPlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Look at the top five cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order.
        this.getSpellAbility().addEffect(new SurveilEffect(5));
    }

    private ContingencyPlan(final ContingencyPlan card) {
        super(card);
    }

    @Override
    public ContingencyPlan copy() {
        return new ContingencyPlan(this);
    }
}
