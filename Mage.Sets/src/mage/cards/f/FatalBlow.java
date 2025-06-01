package mage.cards.f;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class FatalBlow extends CardImpl {

    public FatalBlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Destroy target creature that was dealt damage this turn. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_DAMAGED_THIS_TURN));
    }

    private FatalBlow(final FatalBlow card) {
        super(card);
    }

    @Override
    public FatalBlow copy() {
        return new FatalBlow(this);
    }
}
