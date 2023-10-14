
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class StructuralCollapse extends CardImpl {

    public StructuralCollapse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}");


        // Target player sacrifices an artifact and a land. Structural Collapse deals 2 damage to that player.
        this.getSpellAbility().addEffect(new SacrificeEffect(new FilterArtifactPermanent(), 1, "Target player"));
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_LANDS, 1, "Target player")
                .setText("and a land"));
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private StructuralCollapse(final StructuralCollapse card) {
        super(card);
    }

    @Override
    public StructuralCollapse copy() {
        return new StructuralCollapse(this);
    }
}
