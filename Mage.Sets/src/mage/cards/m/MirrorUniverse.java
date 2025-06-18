package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.ExchangeLifeControllerTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class MirrorUniverse extends CardImpl {

    public MirrorUniverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {tap}, Sacrifice Mirror Universe: Exchange life totals with target opponent. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new ExchangeLifeControllerTargetEffect(),
                new TapSourceCost(), IsStepCondition.getMyUpkeep()
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private MirrorUniverse(final MirrorUniverse card) {
        super(card);
    }

    @Override
    public MirrorUniverse copy() {
        return new MirrorUniverse(this);
    }
}
