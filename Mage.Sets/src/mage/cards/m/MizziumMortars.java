package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MizziumMortars extends CardImpl {

    public MizziumMortars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // MizziumMortars deals 4 damage to target creature you don't control.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));

        // Overload {3}{R}{R}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new DamageAllEffect(4, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL), new ManaCostsImpl<>("{3}{R}{R}{R}")));
    }

    private MizziumMortars(final MizziumMortars card) {
        super(card);
    }

    @Override
    public MizziumMortars copy() {
        return new MizziumMortars(this);
    }
}
