
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class IntimidationBolt extends CardImpl {

    public IntimidationBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{W}");




        // Intimidation Bolt deals 3 damage to target creature. Other creatures can't attack this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new IntimidationEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
    }

    public IntimidationBolt(final IntimidationBolt card) {
        super(card);
    }

    @Override
    public IntimidationBolt copy() {
        return new IntimidationBolt(this);
    }
}

class IntimidationEffect extends RestrictionEffect {

    public IntimidationEffect(Duration duration) {
        super(duration);
        staticText = "Other creatures can't attack this turn";
    }

    public IntimidationEffect(final IntimidationEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (!permanent.getId().equals(source.getFirstTarget())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public IntimidationEffect copy() {
        return new IntimidationEffect(this);
    }

}