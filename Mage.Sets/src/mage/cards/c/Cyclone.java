
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;



/**
 *
 * @author MarcoMarin
 */
public final class Cyclone extends CardImpl {
    
    public Cyclone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");

        // At the beginning of your upkeep, put a wind counter on Cyclone, then sacrifice Cyclone unless you pay {G} for each wind counter on it. If you pay, Cyclone deals damage equal to the number of wind counters on it to each creature and each player.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.WIND.createInstance()));
        ability.addEffect(new CycloneEffect());
        this.addAbility(ability);        
    }

    private Cyclone(final Cyclone card) {
        super(card);
    }

    @Override
    public Cyclone copy() {
        return new Cyclone(this);
    }
}
class CycloneEffect extends OneShotEffect {

    CycloneEffect() {
        super(Outcome.Damage);
        this.staticText = ", then sacrifice {this} unless you pay {G} for each wind counter on it. If you pay, {this} deals damage equal to the number of wind counters on it to each creature and each player";
    }

    private CycloneEffect(final CycloneEffect effect) {
        super(effect);
    }

    @Override
    public CycloneEffect copy() {
        return new CycloneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        int total = permanent.getCounters(game).getCount(CounterType.WIND);
        StringBuilder greens = new StringBuilder(total);
        for (int i=0; i < total; i++){
            greens.append("{G}");
        }
                  
        if(this.choice(game, source, player, new ManaCostsImpl<>(greens.toString()))){
            DamageEverythingEffect dmg = new DamageEverythingEffect(total);
            dmg.apply(game, source);
        } else {            
            permanent.sacrifice(source, game);
        }
        return true;
    }
    
    private boolean choice(Game game, Ability source, Player player, Cost counters) {
        return counters.canPay(source, source, player.getId(), game)
                    && player.chooseUse(Outcome.Damage, "Pay Cyclone's Upkeep?", source, game);
    }
        
}
