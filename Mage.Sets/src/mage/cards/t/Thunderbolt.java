
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author North
 */
public final class Thunderbolt extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Thunderbolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one - Thunderbolt deals 3 damage to target player; or Thunderbolt deals 4 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        Mode mode = new Mode(new DamageTargetEffect(4));
        mode.addTarget(new TargetCreaturePermanent(filter));
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
