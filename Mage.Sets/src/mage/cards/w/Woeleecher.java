
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class Woeleecher extends CardImpl {

    public Woeleecher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {W}, {tap}: Remove a -1/-1 counter from target creature. If you do, you gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WoeleecherEffect(), new ManaCostsImpl("{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private Woeleecher(final Woeleecher card) {
        super(card);
    }

    @Override
    public Woeleecher copy() {
        return new Woeleecher(this);
    }
}

class WoeleecherEffect extends OneShotEffect {

    private int numberCountersOriginal = 0;
    private int numberCountersAfter = 0;

    public WoeleecherEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Remove a -1/-1 counter from target creature. If you do, you gain 2 life";
    }

    public WoeleecherEffect(final WoeleecherEffect effect) {
        super(effect);
    }

    @Override
    public WoeleecherEffect copy() {
        return new WoeleecherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (target != null && you != null) {
            numberCountersOriginal = target.getCounters(game).getCount(CounterType.M1M1);
            target.removeCounters(CounterType.M1M1.createInstance(), source, game);
            numberCountersAfter = target.getCounters(game).getCount(CounterType.M1M1);
            if (numberCountersAfter < numberCountersOriginal && you != null) {
                you.gainLife(2, game, source);
                return true;
            }
        }
        return false;
    }
}