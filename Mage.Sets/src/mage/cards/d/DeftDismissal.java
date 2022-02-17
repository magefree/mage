package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author Styxo
 */
public final class DeftDismissal extends CardImpl {

    public DeftDismissal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Deft Dismissal deals 3 damage divided as you choose among one, two, or three target attacking or blocking creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(3, StaticFilters.FILTER_ATTACKING_OR_BLOCKING_CREATURES));
    }

    private DeftDismissal(final DeftDismissal card) {
        super(card);
    }

    @Override
    public DeftDismissal copy() {
        return new DeftDismissal(this);
    }
}
