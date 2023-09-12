
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Viserion
 */
public final class PistusStrike extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public PistusStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new PoisonControllerTargetCreatureEffect());
    }

    private PistusStrike(final PistusStrike card) {
        super(card);
    }

    @Override
    public PistusStrike copy() {
        return new PistusStrike(this);
    }
}

class PoisonControllerTargetCreatureEffect extends OneShotEffect {

    public PoisonControllerTargetCreatureEffect() {
        super(Outcome.Damage);
        staticText = "Its controller gets a poison counter";
    }

    private PoisonControllerTargetCreatureEffect(final PoisonControllerTargetCreatureEffect effect) {
        super(effect);
    }

    @Override
    public PoisonControllerTargetCreatureEffect copy() {
        return new PoisonControllerTargetCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.addCounters(CounterType.POISON.createInstance(), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }
}
