package mage.cards.f;

import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FarsightRitual extends CardImpl {

    public FarsightRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Bargain
        this.addAbility(new BargainAbility());

        // Look at the top four cards of your library. If this spell was bargained, look at the top eight cards of your library instead. Put two of them into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LookLibraryAndPickControllerEffect(
                        8, 2,
                        PutCards.HAND, PutCards.BOTTOM_RANDOM),
                new LookLibraryAndPickControllerEffect(
                        4, 2,
                        PutCards.HAND, PutCards.BOTTOM_RANDOM),
                BargainedCondition.instance,
                "Look at the top four cards of your library. If this spell was bargained, "
                        + "look at the top eight cards of your library instead. Put two of them into "
                        + "your hand and the rest on the bottom of your library in a random order."
        ));
    }

    private FarsightRitual(final FarsightRitual card) {
        super(card);
    }

    @Override
    public FarsightRitual copy() {
        return new FarsightRitual(this);
    }
}
