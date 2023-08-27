package mage.cards.t;

import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThunderousDebut extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("up to two creature cards");

    public ThunderousDebut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{G}{G}");

        // Bargain
        this.addAbility(new BargainAbility());

        // Look at the top twenty cards of your library. You may reveal up to two creature cards from among them. If this spell was bargained, put the revealed cards onto the battlefield. Otherwise, put the revealed cards into your hand. Then shuffle.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LookLibraryAndPickControllerEffect(
                        20, 2, filter,
                        PutCards.BATTLEFIELD, PutCards.SHUFFLE, false),
                new LookLibraryAndPickControllerEffect(
                        20, 2, filter,
                        PutCards.HAND, PutCards.SHUFFLE, false),
                BargainedCondition.instance,
                "Look at the top twenty cards of your library. You may reveal up to two creature cards "
                        + "from among them. If this spell was bargained, put the revealed cards onto the battlefield. "
                        + "Otherwise, put the revealed cards into your hand. Then shuffle."
        ));
    }

    private ThunderousDebut(final ThunderousDebut card) {
        super(card);
    }

    @Override
    public ThunderousDebut copy() {
        return new ThunderousDebut(this);
    }
}