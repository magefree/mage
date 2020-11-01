package mage.cards.n;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NaturalReclamation extends CardImpl {

    public NaturalReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}");

        // Cascade
        this.addAbility(new CascadeAbility());

        // Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    private NaturalReclamation(final NaturalReclamation card) {
        super(card);
    }

    @Override
    public NaturalReclamation copy() {
        return new NaturalReclamation(this);
    }
}
