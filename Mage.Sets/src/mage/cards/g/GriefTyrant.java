
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GriefTyrant extends CardImpl {
    
    public GriefTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B/R}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Grief Tyrant enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4)),"with four -1/-1 counters on it"));

        // When Grief Tyrant dies, put a -1/-1 counter on target creature for each -1/-1 counter on Grief Tyrant.
        Ability ability = new DiesSourceTriggeredAbility(new GriefTyrantEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }
    
    private GriefTyrant(final GriefTyrant card) {
        super(card);
    }
    
    @Override
    public GriefTyrant copy() {
        return new GriefTyrant(this);
    }
}

class GriefTyrantEffect extends OneShotEffect {
    
    public GriefTyrantEffect() {
        super(Outcome.Neutral);
        this.staticText = "put a -1/-1 counter on target creature for each -1/-1 counter on {this}";
    }
    
    public GriefTyrantEffect(final GriefTyrantEffect effect) {
        super(effect);
    }
    
    @Override
    public GriefTyrantEffect copy() {
        return new GriefTyrantEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent griefTyrant = game.getPermanentOrLKIBattlefield(source.getSourceId());
        int countersOnGriefTyrant = griefTyrant.getCounters(game).getCount(CounterType.M1M1);
        if (targetCreature != null) {
            targetCreature.addCounters(CounterType.M1M1.createInstance(countersOnGriefTyrant), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }
}
