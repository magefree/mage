
package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TakeDown extends CardImpl {

    public TakeDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose one —
        // • Take Down deals 4 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));

        // • Take Down deals 1 damage to each creature with flying
        Mode mode = new Mode(new DamageAllEffect(1, StaticFilters.FILTER_CREATURE_FLYING));
        this.getSpellAbility().addMode(mode);
    }

    private TakeDown(final TakeDown card) {
        super(card);
    }

    @Override
    public TakeDown copy() {
        return new TakeDown(this);
    }
}
