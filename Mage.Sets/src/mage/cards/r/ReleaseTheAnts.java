
package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.ReturnToHandSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class ReleaseTheAnts extends CardImpl {

    public ReleaseTheAnts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Release the Ants deals 1 damage to any target. Clash with an opponent. If you win, return Release the Ants to its owner's hand.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DoIfClashWonEffect(ReturnToHandSpellEffect.getInstance()));
    }

    private ReleaseTheAnts(final ReleaseTheAnts card) {
        super(card);
    }

    @Override
    public ReleaseTheAnts copy() {
        return new ReleaseTheAnts(this);
    }
}
