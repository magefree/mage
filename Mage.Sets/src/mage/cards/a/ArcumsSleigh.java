package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.DefendingPlayerControlsNoSourceCondition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TurnPhase;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Cguy7777
 */
public final class ArcumsSleigh extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public ArcumsSleigh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {tap}: Target creature gains vigilance until end of turn.
        // Activate only during combat and only if defending player controls a snow land.
        CompoundCondition condition = new CompoundCondition(
                "during combat and only if defending player controls a snow land",
                new IsPhaseCondition(TurnPhase.COMBAT, false), // Only during combat
                new DefendingPlayerControlsNoSourceCondition(filter) // Only if defending player controls a snow land
        );

        Ability ability = new ConditionalActivatedAbility(
                new GainAbilityTargetEffect(VigilanceAbility.getInstance()), new GenericManaCost(2), condition);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ArcumsSleigh(final ArcumsSleigh card) {
        super(card);
    }

    @Override
    public ArcumsSleigh copy() {
        return new ArcumsSleigh(this);
    }
}
