
package mage.cards.v;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class Vertigo extends CardImpl {

    public Vertigo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Vertigo deals 2 damage to target creature with flying. That creature loses flying until end of turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                .setText("That creature loses flying until end of turn"));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
    }

    private Vertigo(final Vertigo card) {
        super(card);
    }

    @Override
    public Vertigo copy() {
        return new Vertigo(this);
    }
}
