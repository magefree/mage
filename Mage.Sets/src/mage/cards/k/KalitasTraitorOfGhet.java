/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author fireshoes
 */
public class KalitasTraitorOfGhet extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Vampire or Zombie");

    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.or(new SubtypePredicate(SubType.VAMPIRE),
                (new SubtypePredicate(SubType.ZOMBIE))));
    }

    public KalitasTraitorOfGhet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Vampire");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // If a nontoken creature an opponent controls would die, instead exile that card and create a 2/2 black Zombie creature token.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KalitasTraitorOfGhetEffect()));

        // {2}{B}, Sacrifice another Vampire or Zombie: Put two +1/+1 counters on Kalitas, Traitor of Ghet.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl("{2}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);
    }

    public KalitasTraitorOfGhet(final KalitasTraitorOfGhet card) {
        super(card);
    }

    @Override
    public KalitasTraitorOfGhet copy() {
        return new KalitasTraitorOfGhet(this);
    }
}

class KalitasTraitorOfGhetEffect extends ReplacementEffectImpl {

    public KalitasTraitorOfGhetEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a nontoken creature an opponent controls would die, instead exile that card and create a 2/2 black Zombie creature token";
    }

    public KalitasTraitorOfGhetEffect(final KalitasTraitorOfGhetEffect effect) {
        super(effect);
    }

    @Override
    public KalitasTraitorOfGhetEffect copy() {
        return new KalitasTraitorOfGhetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent != null) {
                    controller.moveCards(permanent, Zone.EXILED, source, game);
                    new CreateTokenEffect(new ZombieToken()).apply(game, source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent != null && game.getOpponents(source.getControllerId()).contains(permanent.getControllerId()) && !(permanent instanceof PermanentToken)) {
                if (zEvent.getTarget() != null) { // if it comes from permanent, check if it was a creature on the battlefield
                    if (zEvent.getTarget().isCreature()) {
                        return true;
                    }
                } else if (permanent.isCreature()) {
                    return true;
                }
            }
        }
        return false;
    }

}
