
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DevilToken;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class MakeMischief extends CardImpl {

    public MakeMischief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Make Mischief deals 1 damage to any target. Create a 1/1 red Devil creature token.
        // It has "When this creature dies, it deals 1 damage to any target."
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DevilToken()));
    }

    private MakeMischief(final MakeMischief card) {
        super(card);
    }

    @Override
    public MakeMischief copy() {
        return new MakeMischief(this);
    }
}
