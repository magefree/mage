package mage.cards.l;

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
public final class LashOfTheBalrog extends CardImpl {

    public LashOfTheBalrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature or pay {4}.
        this.getSpellAbility().addCost(new OrCost(
                "sacrifice a creature or pay {4}",
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT),
                new GenericManaCost(4)
        ));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LashOfTheBalrog(final LashOfTheBalrog card) {
        super(card);
    }

    @Override
    public LashOfTheBalrog copy() {
        return new LashOfTheBalrog(this);
    }
}
