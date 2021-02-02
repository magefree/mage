
package mage.cards.c;

import java.util.UUID;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawDiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class CarefulConsideration extends CardImpl {

    public CarefulConsideration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");


        // Target player draws four cards, then discards three cards. If you cast this spell during your main phase, instead that player draws four cards, then discards two cards.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawDiscardTargetEffect(4,2),
                new DrawDiscardTargetEffect(4,3),
                AddendumCondition.instance,
                "Target player draws four cards, then discards three cards. If you cast this spell during your main phase, instead that player draws four cards, then discards two cards"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private CarefulConsideration(final CarefulConsideration card) {
        super(card);
    }

    @Override
    public CarefulConsideration copy() {
        return new CarefulConsideration(this);
    }
}
