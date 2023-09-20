
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class SoulsMight extends CardImpl {

    public SoulsMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}");


        // Put X +1/+1 counters on target creature, where X is that creature's power.
        this.getSpellAbility().addEffect(new SoulsMightEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SoulsMight(final SoulsMight card) {
        super(card);
    }

    @Override
    public SoulsMight copy() {
        return new SoulsMight(this);
    }
}

class SoulsMightEffect extends OneShotEffect {

    public SoulsMightEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put X +1/+1 counters on target creature, where X is that creature's power";
    }

    private SoulsMightEffect(final SoulsMightEffect effect) {
        super(effect);
    }

    @Override
    public SoulsMightEffect copy() {
        return new SoulsMightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && permanent.getPower().getValue() > 0) {
            permanent.addCounters(CounterType.P1P1.createInstance(permanent.getPower().getValue()), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }
}
