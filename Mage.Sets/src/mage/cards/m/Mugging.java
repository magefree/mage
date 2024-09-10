
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class Mugging extends CardImpl {

    public Mugging(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");


        // Mugging deals 2 damage to target creature. That creature can't block this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn)
                .setText("That creature can't block this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Mugging(final Mugging card) {
        super(card);
    }

    @Override
    public Mugging copy() {
        return new Mugging(this);
    }
}
