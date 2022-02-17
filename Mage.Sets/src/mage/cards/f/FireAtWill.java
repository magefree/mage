package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class FireAtWill extends CardImpl {

    public FireAtWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R/W}{R/W}{R/W}");

        // Fire at Will deals 3 damage divided as you choose among one, two, or three target attacking or blocking creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(3, StaticFilters.FILTER_ATTACKING_OR_BLOCKING_CREATURES));
    }

    private FireAtWill(final FireAtWill card) {
        super(card);
    }

    @Override
    public FireAtWill copy() {
        return new FireAtWill(this);
    }
}
