
package mage.cards.t;

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
public final class TezzeretsAmbition extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("you control no artifacts");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public TezzeretsAmbition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Draw three cards. If you control no artifacts, discard a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardControllerEffect(1),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0)));
    }

    private TezzeretsAmbition(final TezzeretsAmbition card) {
        super(card);
    }

    @Override
    public TezzeretsAmbition copy() {
        return new TezzeretsAmbition(this);
    }
}
