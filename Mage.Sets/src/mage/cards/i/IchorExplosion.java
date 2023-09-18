package mage.cards.i;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author awjackson
 */
public final class IchorExplosion extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(SacrificeCostCreaturesPower.instance, false);

    public IchorExplosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // As an additional cost to cast Ichor Explosion, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // All creatures get -X/-X until end of turn, where X is the sacrificed creature's power.
        this.getSpellAbility().addEffect(new BoostAllEffect(xValue, xValue, Duration.EndOfTurn));
    }

    private IchorExplosion(final IchorExplosion card) {
        super(card);
    }

    @Override
    public IchorExplosion copy() {
        return new IchorExplosion(this);
    }
}
