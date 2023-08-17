package mage.cards.r;

import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RowansGrimSearch extends CardImpl {

    public RowansGrimSearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Bargain
        this.addAbility(new BargainAbility());

        // If this spell was bargained, look at the top four cards of your library, then put up to two of them back on top of your library in any order and the rest into your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LookLibraryAndPickControllerEffect(4, 2, PutCards.TOP_ANY, PutCards.GRAVEYARD),
                BargainedCondition.instance,
                "If this spell was bargained, look at the top four cards of your library, "
                        + "then put up to two of them back on top of your library in any order and the rest into your graveyard."
        ));

        // You draw two cards and you lose 2 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2, "you").concatBy("<br>"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
    }

    private RowansGrimSearch(final RowansGrimSearch card) {
        super(card);
    }

    @Override
    public RowansGrimSearch copy() {
        return new RowansGrimSearch(this);
    }
}