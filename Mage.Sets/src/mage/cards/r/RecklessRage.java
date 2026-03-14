package mage.cards.r;

import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class RecklessRage extends CardImpl {

    public RecklessRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Reckless Rage deals 4 damage to target creature you don't control and 2 damage to target creature you control.
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(4, 2));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(2));
    }

    private RecklessRage(final RecklessRage card) {
        super(card);
    }

    @Override
    public RecklessRage copy() {
        return new RecklessRage(this);
    }
}
