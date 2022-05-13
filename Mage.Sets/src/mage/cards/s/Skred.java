
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
/**
 *
 * @author shieldal
 */
public final class Skred extends CardImpl {
    
    public Skred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        
        //Skred deals damage to target creature equal to the number of snow permanents you control.
        this.getSpellAbility().addEffect(new SkredDamageEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Skred(final Skred card) {
        super(card);
    }

    @Override
    public Skred copy() {
        return new Skred(this);
    }
}

class SkredDamageEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("equal to the number of snow permanents you control.");
    static {
        filter.add(SuperType.SNOW.getPredicate());
    }
    
    public SkredDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to target creature equal to the number of snow permanents you control.";
    }

    public SkredDamageEffect(final SkredDamageEffect effect) {
        super(effect);
    }

    @Override
    public SkredDamageEffect copy() {
        return new SkredDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if(amount > 0) {   
            if (permanent != null) {
                permanent.damage(amount, source.getSourceId(), source, game, false, true);
                return true;
            }
        }
        return false;
    }
}