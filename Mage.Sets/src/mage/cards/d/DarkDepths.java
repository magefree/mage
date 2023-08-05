
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MaritLageToken;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class DarkDepths extends CardImpl {

    public DarkDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.supertype.add(SuperType.SNOW);

        // Dark Depths enters the battlefield with ten ice counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.ICE.createInstance(10)), "with ten ice counters on it"));
        // {3}: Remove an ice counter from Dark Depths.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.ICE.createInstance(1)), new ManaCostsImpl<>("{3}")));
        // When Dark Depths has no ice counters on it, sacrifice it. If you do, create a legendary 20/20 black Avatar creature token with flying and "This creature is indestructible" named Marit Lage.
        this.addAbility(new DarkDepthsAbility());
    }

    private DarkDepths(final DarkDepths card) {
        super(card);
    }

    @Override
    public DarkDepths copy() {
        return new DarkDepths(this);
    }
}

class DarkDepthsSacrificeEffect extends SacrificeSourceEffect {

    private boolean sacrificed = false;

    public DarkDepthsSacrificeEffect() {
        super();
    }

    public DarkDepthsSacrificeEffect(final DarkDepthsSacrificeEffect effect) {
        super(effect);
        this.sacrificed = effect.sacrificed;
    }

    @Override
    public DarkDepthsSacrificeEffect copy() {
        return new DarkDepthsSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        sacrificed = super.apply(game, source);
        if (sacrificed) {
            new CreateTokenEffect(new MaritLageToken()).apply(game, source);
        }
        return sacrificed;
    }

    public boolean isSacrificed() {
        return sacrificed;
    }
}

class DarkDepthsAbility extends StateTriggeredAbility {

    public DarkDepthsAbility() {
        super(Zone.BATTLEFIELD, new DarkDepthsSacrificeEffect());
    }

    public DarkDepthsAbility(final DarkDepthsAbility ability) {
        super(ability);
    }

    @Override
    public DarkDepthsAbility copy() {
        return new DarkDepthsAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.ICE) == 0;
    }

    @Override
    public String getRule() {
        return "When {this} has no ice counters on it, sacrifice it. If you do, " +
                "create Marit Lage, a legendary 20/20 black Avatar creature token with flying and indestructible.";
    }

}
