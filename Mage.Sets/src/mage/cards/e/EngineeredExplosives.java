
package mage.cards.e;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class EngineeredExplosives extends CardImpl {


    public EngineeredExplosives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{X}");

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // {2}, Sacrifice Engineered Explosives: Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Engineered Explosives.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EngineeredExplosivesEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EngineeredExplosives(final EngineeredExplosives card) {
        super(card);
    }

    @Override
    public EngineeredExplosives copy() {
        return new EngineeredExplosives(this);
    }
}

class EngineeredExplosivesEffect extends OneShotEffect {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();
   

    public EngineeredExplosivesEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent with mana value equal to the number of charge counters on Engineered Explosives";
    }


    public EngineeredExplosivesEffect(final EngineeredExplosivesEffect effect) {
        super(effect);
    }

    @Override
    public EngineeredExplosivesEffect copy() {
        return new EngineeredExplosivesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject engineeredExplosives = game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if(engineeredExplosives instanceof Permanent){
            int count = ((Permanent)engineeredExplosives).getCounters(game).getCount(CounterType.CHARGE);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if(permanent.getManaValue() == count){
                    permanent.destroy(source, game, false);
                }
            }
            return true;
        }
        return false;
    }

}
