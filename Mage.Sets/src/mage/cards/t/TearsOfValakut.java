
package mage.cards.t;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TearsOfValakut extends CardImpl {

    public TearsOfValakut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility().setRuleAtTheTop(true));

        // Tears of Valakut deals 5 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
    }

    private TearsOfValakut(final TearsOfValakut card) {
        super(card);
    }

    @Override
    public TearsOfValakut copy() {
        return new TearsOfValakut(this);
    }
}
