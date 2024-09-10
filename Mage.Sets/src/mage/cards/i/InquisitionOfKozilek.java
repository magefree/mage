

package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class InquisitionOfKozilek extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonland card with mana value 3 or less");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public InquisitionOfKozilek(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter));
    }

    private InquisitionOfKozilek(final InquisitionOfKozilek card) {
        super(card);
    }

    @Override
    public InquisitionOfKozilek copy() {
        return new InquisitionOfKozilek(this);
    }
}
