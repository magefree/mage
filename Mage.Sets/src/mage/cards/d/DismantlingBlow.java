
package mage.cards.d;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DismantlingBlow extends CardImpl {

    public DismantlingBlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Kicker {2}{U}
        this.addAbility(new KickerAbility("{2}{U}"));
        // Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        // If Dismantling Blow was kicked, draw two cards.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(2),
                KickedCondition.ONCE,
                "if this spell was kicked, draw two cards"));
    }

    private DismantlingBlow(final DismantlingBlow card) {
        super(card);
    }

    @Override
    public DismantlingBlow copy() {
        return new DismantlingBlow(this);
    }
}
