package mage.cards.c;

import mage.abilities.effects.common.DamageTargetAndYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class Char extends CardImpl {

    public Char(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Char deals 4 damage to any target and 2 damage to you.
        this.getSpellAbility().addEffect(new DamageTargetAndYouEffect(4, 2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private Char(final Char card) {
        super(card);
    }

    @Override
    public Char copy() {
        return new Char(this);
    }
}
