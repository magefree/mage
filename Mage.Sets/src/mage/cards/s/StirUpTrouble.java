package mage.cards.s;

import java.util.UUID;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author muz
 */
public final class StirUpTrouble extends CardImpl {

    public StirUpTrouble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice an artifact or creature or pay {4}.
        this.getSpellAbility().addCost(new OrCost(
            "sacrifice an artifact or creature or pay {4}",
            new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE), new GenericManaCost(4)
        ));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private StirUpTrouble(final StirUpTrouble card) {
        super(card);
    }

    @Override
    public StirUpTrouble copy() {
        return new StirUpTrouble(this);
    }
}
