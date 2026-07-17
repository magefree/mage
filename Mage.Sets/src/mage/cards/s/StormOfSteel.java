package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author muz
 */
public final class StormOfSteel extends CardImpl {

    public StormOfSteel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Storm of Steel deals 2 damage to each of one or two targets.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2).withTargetDescription("each of one or two targets"));
        this.getSpellAbility().addTarget(new TargetAnyTarget(1, 2));
    }

    private StormOfSteel(final StormOfSteel card) {
        super(card);
    }

    @Override
    public StormOfSteel copy() {
        return new StormOfSteel(this);
    }
}
