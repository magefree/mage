package mage.cards.e;

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
public final class Electrickery extends CardImpl {

    public Electrickery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Electrickery deals 1 damage to target creature you don't control.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));

        // Overload {1}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new DamageAllEffect(1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL), new ManaCostsImpl<>("{1}{R}")));
    }

    private Electrickery(final Electrickery card) {
        super(card);
    }

    @Override
    public Electrickery copy() {
        return new Electrickery(this);
    }
}
