
package mage.cards.m;

import java.util.UUID;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class MoggSalvage extends CardImpl {

    private static final FilterPermanent filterMountain = new FilterPermanent();
    private static final FilterPermanent filterIsland = new FilterPermanent();

    static {
        filterMountain.add(SubType.MOUNTAIN.getPredicate());
        filterIsland.add(SubType.ISLAND.getPredicate());
    }

    public MoggSalvage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // If an opponent controls an Island and you control a Mountain, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls an Island and you control a Mountain",
                new OpponentControlsPermanentCondition(filterIsland),
                new PermanentsOnTheBattlefieldCondition(filterMountain));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));

        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private MoggSalvage(final MoggSalvage card) {
        super(card);
    }

    @Override
    public MoggSalvage copy() {
        return new MoggSalvage(this);
    }
}
