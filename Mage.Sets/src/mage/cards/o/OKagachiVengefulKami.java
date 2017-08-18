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
package mage.cards.o;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayersAttackedLastTurnWatcher;

/**
 *
 * @author spjspj
 */
public class OKagachiVengefulKami extends CardImpl {

    public OKagachiVengefulKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}{R}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Dragon");
        this.subtype.add("Spirit");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever O-Kagachi, Vengeful Kami deals combat damage to a player, if that player attacked you during his or her last turn, exile target nonland permanent that player controls
        OKagachiVengefulKamiTriggeredAbility ability = new OKagachiVengefulKamiTriggeredAbility();
        ability.addWatcher(new PlayersAttackedLastTurnWatcher());
        this.addAbility(ability);
    }

    public OKagachiVengefulKami(final OKagachiVengefulKami card) {
        super(card);
    }

    @Override
    public OKagachiVengefulKami copy() {
        return new OKagachiVengefulKami(this);
    }
}

class OKagachiVengefulKamiTriggeredAbility extends TriggeredAbilityImpl {

    private boolean madeDamge = false;
    private Set<UUID> damagedPlayers = new HashSet<>();

    public OKagachiVengefulKamiTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OKagachiVengefulKamiEffect(), false);
    }

    public OKagachiVengefulKamiTriggeredAbility(final OKagachiVengefulKamiTriggeredAbility ability) {
        super(ability);
        this.madeDamge = ability.madeDamge;
        this.damagedPlayers = new HashSet<>();
        this.damagedPlayers.addAll(ability.damagedPlayers);
    }

    @Override
    public OKagachiVengefulKamiTriggeredAbility copy() {
        return new OKagachiVengefulKamiTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            UUID damagedPlayerId = game.getCombat().getDefenderId(sourceId);
            UUID you = this.getControllerId();
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getId().equals(this.getSourceId())) {
                PlayersAttackedLastTurnWatcher watcher = (PlayersAttackedLastTurnWatcher) game.getState().getWatchers().get(PlayersAttackedLastTurnWatcher.class.getSimpleName());
                if (watcher != null && watcher.attackedLastTurn(damagedPlayerId, you)) {
                    FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent defending player controls");
                    filter.add(new ControllerIdPredicate(damagedPlayerId));
                    this.getTargets().clear();
                    TargetPermanent target = new TargetPermanent(filter);
                    this.addTarget(target);

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, if that player attacked you during his or her last turn, exile target nonland permanent that player controls";
    }
}

class OKagachiVengefulKamiEffect extends OneShotEffect {

    public OKagachiVengefulKamiEffect() {
        super(Outcome.Benefit);
        this.staticText = "if that player attacked you during his or her last turn, exile target nonland permanent that player controls";
    }

    public OKagachiVengefulKamiEffect(final OKagachiVengefulKamiEffect effect) {
        super(effect);
    }

    @Override
    public OKagachiVengefulKamiEffect copy() {
        return new OKagachiVengefulKamiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return new ExileTargetEffect().apply(game, source);
        }
        return false;
    }
}
