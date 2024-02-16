
package mage.cards.h;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class HypnoticCloud extends CardImpl {

    public HypnoticCloud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");


        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));
        // Target player discards a card. If Hypnotic Cloud was kicked, that player discards three cards instead.

        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DiscardTargetEffect(3), new DiscardTargetEffect(1), KickedCondition.ONCE,
                "Target player discards a card. If this spell was kicked, that player discards three cards instead"));

    }

    private HypnoticCloud(final HypnoticCloud card) {
        super(card);
    }

    @Override
    public HypnoticCloud copy() {
        return new HypnoticCloud(this);
    }
}
