
package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author North
 */
public final class Thunderbolt extends CardImpl {

    public Thunderbolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one - Thunderbolt deals 3 damage to target player; or Thunderbolt deals 4 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        Mode mode = new Mode(new DamageTargetEffect(4));
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.getSpellAbility().addMode(mode);
    }

    private Thunderbolt(final Thunderbolt card) {
        super(card);
    }

    @Override
    public Thunderbolt copy() {
        return new Thunderbolt(this);
    }
}
