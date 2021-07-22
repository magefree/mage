package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RipApart extends CardImpl {

    public RipApart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{W}");

        // Choose one —
        // • Rip Apart deals 3 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // • Destroy target artifact or enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addMode(mode);
    }

    private RipApart(final RipApart card) {
        super(card);
    }

    @Override
    public RipApart copy() {
        return new RipApart(this);
    }
}
