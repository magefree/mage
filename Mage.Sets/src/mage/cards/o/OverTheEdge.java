package mage.cards.o;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverTheEdge extends CardImpl {

    public OverTheEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Choose one --
        // * Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // * Target creature you control explores, then it explores again.
        this.getSpellAbility().addMode(new Mode(new ExploreTargetEffect(false))
                .addEffect(new ExploreTargetEffect().setText(", then it explores again"))
                .addTarget(new TargetControlledCreaturePermanent()));
    }

    private OverTheEdge(final OverTheEdge card) {
        super(card);
    }

    @Override
    public OverTheEdge copy() {
        return new OverTheEdge(this);
    }
}
