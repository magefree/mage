
package mage.cards.v;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ViciousOffering extends CardImpl {

    public ViciousOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Kicker—Sacrifice a creature.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // Target creature gets -2/-2 until end of turn. If this spell was kicked, that creature gets -5/-5 until end of turn instead.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new BoostTargetEffect(-5, -5, Duration.EndOfTurn),
                new BoostTargetEffect(-2, -2, Duration.EndOfTurn), new LockedInCondition(KickedCondition.ONCE),
                "Target creature gets -2/-2 until end of turn. If this spell was kicked, that creature gets -5/-5 until end of turn instead."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("creature that gets -/-"));
    }

    private ViciousOffering(final ViciousOffering card) {
        super(card);
    }

    @Override
    public ViciousOffering copy() {
        return new ViciousOffering(this);
    }
}
