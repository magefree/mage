
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.EldraziSpawnToken;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class SpawningBreath extends CardImpl {

    public SpawningBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EldraziSpawnToken()));
    }

    private SpawningBreath(final SpawningBreath card) {
        super(card);
    }

    @Override
    public SpawningBreath copy() {
        return new SpawningBreath(this);
    }
}
