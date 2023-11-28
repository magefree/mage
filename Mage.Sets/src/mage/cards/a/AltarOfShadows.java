
package mage.cards.a;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class AltarOfShadows extends CardImpl {

    public AltarOfShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{7}");

        // At the beginning of your precombat main phase, add {B} for each charge counter on Altar of Shadows.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(new AltarOfShadowsEffect(), TargetController.YOU, false));
        
        // {7}, {tap}: Destroy target creature. Then put a charge counter on Altar of Shadows.
        Ability destroyAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new GenericManaCost(7));
        destroyAbility.addCost(new TapSourceCost());
        destroyAbility.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true)
                .concatBy("Then"));
        destroyAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(destroyAbility);
    }

    private AltarOfShadows(final AltarOfShadows card) {
        super(card);
    }

    @Override
    public AltarOfShadows copy() {
        return new AltarOfShadows(this);
    }
}

class AltarOfShadowsEffect extends OneShotEffect {

    public AltarOfShadowsEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "add {B} for each charge counter on Altar of Shadows";
    }

    private AltarOfShadowsEffect(final AltarOfShadowsEffect effect) {
        super(effect);
    }

    @Override
    public AltarOfShadowsEffect copy() {
        return new AltarOfShadowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (sourcePermanent != null && player != null) {
            int chargeCounters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE);
            if (chargeCounters > 0){
                player.getManaPool().addMana(Mana.BlackMana(chargeCounters), game, source);
                return true;
            }
        }
        return false;
    }
}
