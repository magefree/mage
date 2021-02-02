package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SeveredStrands extends CardImpl {

    public SeveredStrands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, true)));

        // You gain life equal to the sacrificed creature's toughness. Destroy target creature an opponent controls.
        this.getSpellAbility().addEffect(new GainLifeEffect(
                SacrificeCostCreaturesToughness.instance,
                "You gain life equal to the "
                + "sacrificed creature's toughness."
        ));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private SeveredStrands(final SeveredStrands card) {
        super(card);
    }

    @Override
    public SeveredStrands copy() {
        return new SeveredStrands(this);
    }
}
