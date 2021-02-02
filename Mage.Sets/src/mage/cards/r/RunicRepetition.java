
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author BetaSteward
 */
public final class RunicRepetition extends CardImpl {

    private static final FilterCard filter = new FilterCard("exiled card with flashback you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(new AbilityPredicate(FlashbackAbility.class));
    }

    public RunicRepetition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Return target exiled card with flashback you own to your hand.
        TargetCardInExile target = new TargetCardInExile(filter);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    private RunicRepetition(final RunicRepetition card) {
        super(card);
    }

    @Override
    public RunicRepetition copy() {
        return new RunicRepetition(this);
    }

}
