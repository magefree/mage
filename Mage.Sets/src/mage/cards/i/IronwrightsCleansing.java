
package mage.cards.i;

import mage.abilities.effects.common.ExileTargetEffect;
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
public final class IronwrightsCleansing extends CardImpl {

    public IronwrightsCleansing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Exile target artifact or enchantment.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

    }

    private IronwrightsCleansing(final IronwrightsCleansing card) {
        super(card);
    }

    @Override
    public IronwrightsCleansing copy() {
        return new IronwrightsCleansing(this);
    }
}
