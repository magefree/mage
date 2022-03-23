
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class Necroplasm extends CardImpl {

    public Necroplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, put a +1/+1 counter on Necroplasm.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false));
        
        // At the beginning of your end step, destroy each creature with converted mana cost equal to the number of +1/+1 counters on Necroplasm.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new NecroplasmEffect(), TargetController.YOU, false));
        
        // Dredge 2
        this.addAbility(new DredgeAbility(2));
    }

    private Necroplasm(final Necroplasm card) {
        super(card);
    }

    @Override
    public Necroplasm copy() {
        return new Necroplasm(this);
    }
}

class NecroplasmEffect extends OneShotEffect {
    
    NecroplasmEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy each creature with mana value equal to the number of +1/+1 counters on {this}.";
    }
    
    NecroplasmEffect(final NecroplasmEffect effect) {
        super(effect);
    }
    
    @Override
    public NecroplasmEffect copy() {
        return new NecroplasmEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            int numCounters = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, numCounters));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if(permanent != null) {
                    permanent.destroy(source, game, false);
                }
            }
            return true;
        }
        return false;
    }
}
