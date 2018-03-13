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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public class SzadekLordOfSecrets extends CardImpl {

    public SzadekLordOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If Szadek, Lord of Secrets would deal combat damage to a player, instead put that many +1/+1 counters on Szadek and that player puts that many cards from the top of his or her library into his or her graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SzadekLordOfSecretsEffect()));

    }

    public SzadekLordOfSecrets(final SzadekLordOfSecrets card) {
        super(card);
    }

    @Override
    public SzadekLordOfSecrets copy() {
        return new SzadekLordOfSecrets(this);
    }
}

class SzadekLordOfSecretsEffect extends ReplacementEffectImpl {

    SzadekLordOfSecretsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If {this} would deal combat damage to a player, instead put that many +1/+1 counters on {this} and that player puts that many cards from the top of his or her library into his or her graveyard";
    }

    SzadekLordOfSecretsEffect(final SzadekLordOfSecretsEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Player damagedPlayer = game.getPlayer(damageEvent.getTargetId());

        if (damageEvent.isCombatDamage()) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(damageEvent.getAmount()), source, game);
                if (damagedPlayer != null) {
                    damagedPlayer.moveCards(damagedPlayer.getLibrary().getTopCards(game, damageEvent.getAmount()), Zone.GRAVEYARD, source, game);
                }
            }
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
            if (damageEvent.isCombatDamage()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SzadekLordOfSecretsEffect copy() {
        return new SzadekLordOfSecretsEffect(this);
    }
}
