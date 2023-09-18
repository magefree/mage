
package mage.cards.a;

import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class AbzanAdvantage extends CardImpl {

    public AbzanAdvantage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Target player sacrifices an enchantment. Bolster 1.
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENT, 1, "Target player"));
        this.getSpellAbility().addEffect(new BolsterEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private AbzanAdvantage(final AbzanAdvantage card) {
        super(card);
    }

    @Override
    public AbzanAdvantage copy() {
        return new AbzanAdvantage(this);
    }
}
