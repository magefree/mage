package mage.cards.a;

import java.util.UUID;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author AhmadYProjects
 */
public final class AnnihilatingGlare extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("");
    static{
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()));
    }
    public AnnihilatingGlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");
        

        // As an additional cost to cast this spell, pay {4} or sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new OrCost("pay {4} or sacrifice an artifact or creature",
                new GenericManaCost(4),
                new SacrificeTargetCost(new FilterControlledCreaturePermanent()),
                new SacrificeTargetCost(new FilterControlledArtifactPermanent())));
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
