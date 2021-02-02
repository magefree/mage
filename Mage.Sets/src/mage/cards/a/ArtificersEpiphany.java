
package mage.cards.a;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ArtificersEpiphany extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control no artifacts");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ArtificersEpiphany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Draw two cards. If you control no artifacts, discard a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardControllerEffect(1),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0)));
    }

    private ArtificersEpiphany(final ArtificersEpiphany card) {
        super(card);
    }

    @Override
    public ArtificersEpiphany copy() {
        return new ArtificersEpiphany(this);
    }
}
