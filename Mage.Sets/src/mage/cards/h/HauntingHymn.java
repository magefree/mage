
package mage.cards.h;

import java.util.UUID;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author nigelzor
 */
public final class HauntingHymn extends CardImpl {

    public HauntingHymn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{B}{B}");

        // Target player discards two cards. If you cast this spell during your main phase, that player discards four cards instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardTargetEffect(4),
                new DiscardTargetEffect(2),
                AddendumCondition.instance,
                "Target player discards two cards. If you cast this spell during your main phase, that player discards four cards instead"));
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private HauntingHymn(final HauntingHymn card) {
        super(card);
    }

    @Override
    public HauntingHymn copy() {
        return new HauntingHymn(this);
    }
}
