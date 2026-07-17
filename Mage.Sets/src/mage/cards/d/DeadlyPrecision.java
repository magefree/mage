package mage.cards.d;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadlyPrecision extends CardImpl {

    public DeadlyPrecision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, pay {4} or sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new OrCost(
                "pay {4} or sacrifice an artifact or creature", new GenericManaCost(4),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE)
        ));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DeadlyPrecision(final DeadlyPrecision card) {
        super(card);
    }

    @Override
    public DeadlyPrecision copy() {
        return new DeadlyPrecision(this);
    }
}
