package mage.cards.w;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitheringTorment extends CardImpl {

    public WitheringTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Destroy target creature or enchantment. You lose 2 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));
    }

    private WitheringTorment(final WitheringTorment card) {
        super(card);
    }

    @Override
    public WitheringTorment copy() {
        return new WitheringTorment(this);
    }
}
