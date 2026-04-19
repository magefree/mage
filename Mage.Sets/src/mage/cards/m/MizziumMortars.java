package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL;

/**
 * @author LevelX2
 */
public final class MizziumMortars extends CardImpl {

    public MizziumMortars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Mizzium Mortars deals 4 damage to target creature you don't control.
        // Overload {3}{R}{R}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{3}{R}{R}{R}"),
                new TargetPermanent(FILTER_CREATURE_YOU_DONT_CONTROL), new DamageTargetEffect(4));
    }

    private MizziumMortars(final MizziumMortars card) {
        super(card);
    }

    @Override
    public MizziumMortars copy() {
        return new MizziumMortars(this);
    }
}
