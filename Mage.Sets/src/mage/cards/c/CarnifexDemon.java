

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class CarnifexDemon extends CardImpl {

    public CarnifexDemon (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)), "{this} enters the battlefield with two -1/-1 counters on it"));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CarnifexDemonEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.M1M1.createInstance()));
        this.addAbility(ability);
    }

    public CarnifexDemon (final CarnifexDemon card) {
        super(card);
    }

    @Override
    public CarnifexDemon copy() {
        return new CarnifexDemon(this);
    }
}

class CarnifexDemonEffect extends OneShotEffect {
    public CarnifexDemonEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Put a -1/-1 counter on each other creature";
    }

    public CarnifexDemonEffect(final CarnifexDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            for (Permanent t : game.getBattlefield().getAllActivePermanents()) {
                if (t.isCreature() && !t.getId().equals(source.getSourceId()))
                    t.addCounters(CounterType.M1M1.createInstance(), source, game);
            }
        }
        return false;
    }

    @Override
    public CarnifexDemonEffect copy() {
        return new CarnifexDemonEffect(this);
    }

}