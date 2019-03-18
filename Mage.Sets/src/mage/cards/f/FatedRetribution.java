
package mage.cards.f;

import java.util.UUID;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public final class FatedRetribution extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures and planeswalkers");
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }
    public FatedRetribution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{W}{W}{W}");


        // Destroy all creatures and planeswalkers. If it's your turn, scry 2.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, false));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ScryEffect(2), MyTurnCondition.instance, "If it's your turn, scry 2"));
    }

    public FatedRetribution(final FatedRetribution card) {
        super(card);
    }

    @Override
    public FatedRetribution copy() {
        return new FatedRetribution(this);
    }
}
