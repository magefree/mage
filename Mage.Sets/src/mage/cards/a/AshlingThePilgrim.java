
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AshlingThePilgrim extends CardImpl {

    public AshlingThePilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}: Put a +1/+1 counter on Ashling the Pilgrim. If this is the third time this ability has resolved this turn, remove all +1/+1 counters from Ashling the Pilgrim, and it deals that much damage to each creature and each player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true), new ManaCostsImpl("{1}{R}"));
        ability.addEffect(new AshlingThePilgrimEffect());
        this.addAbility(ability);
    }

    public AshlingThePilgrim(final AshlingThePilgrim card) {
        super(card);
    }

    @Override
    public AshlingThePilgrim copy() {
        return new AshlingThePilgrim(this);
    }
}

class AshlingThePilgrimEffect extends OneShotEffect {

    static class ActivationInfo {
        public int zoneChangeCounter;
        public int turn;
        public int activations;
    }

    public AshlingThePilgrimEffect() {
        super(Outcome.Damage);
        this.staticText = "If this is the third time this ability has resolved this turn, remove all +1/+1 counters from {this}, and it deals that much damage to each creature and each player";
    }

    public AshlingThePilgrimEffect(final AshlingThePilgrimEffect effect) {
        super(effect);        
    }

    @Override
    public AshlingThePilgrimEffect copy() {
        return new AshlingThePilgrimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            ActivationInfo info;
            Object object = game.getState().getValue(source.getSourceId() + "ActivationInfo");
            if (object instanceof ActivationInfo) {
                info = (ActivationInfo) object;
                if (info.turn != game.getTurnNum() || sourcePermanent.getZoneChangeCounter(game) != info.zoneChangeCounter) {
                    info.turn = game.getTurnNum();
                    info.zoneChangeCounter = sourcePermanent.getZoneChangeCounter(game);
                    info.activations = 0;
                }
            } else {
                info = new ActivationInfo();
                info.turn = game.getTurnNum();
                info.zoneChangeCounter = sourcePermanent.getZoneChangeCounter(game);
                game.getState().setValue(source.getSourceId() + "ActivationInfo", info);
            }
            info.activations++;
            if (info.activations == 3) {
                int damage = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
                if (damage > 0) {
                    sourcePermanent.removeCounters(CounterType.P1P1.getName(), damage, game);
                    return new DamageEverythingEffect(damage, new FilterCreaturePermanent()).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
