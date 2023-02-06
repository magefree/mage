package mage.cards.a;

import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author AhmadYProjects
 */
public final class AnnihilatingGlare extends CardImpl {

    public AnnihilatingGlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");
        

        // As an additional cost to cast this spell, pay {4} or sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new OrCost("pay {4} or sacrifice an artifact or creature",
                new GenericManaCost(4),
                new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE))
        ));
        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private AnnihilatingGlare(final AnnihilatingGlare card) {
        super(card);
    }

    @Override
    public AnnihilatingGlare copy() {
        return new AnnihilatingGlare(this);
    }
}
