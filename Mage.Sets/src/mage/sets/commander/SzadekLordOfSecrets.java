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
package mage.sets.commander;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
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
public class SzadekLordOfSecrets extends CardImpl<SzadekLordOfSecrets> {

    public SzadekLordOfSecrets(UUID ownerId) {
        super(ownerId, 228, "Szadek, Lord of Secrets", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{B}{B}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Vampire");

        this.color.setBlue(true);
        this.color.setBlack(true);
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

class SzadekLordOfSecretsEffect extends ReplacementEffectImpl<SzadekLordOfSecretsEffect> {

    List<Card> cards;

    SzadekLordOfSecretsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If {this} would deal combat damage to a player, instead put that many +1/+1 counters on Szadek and that player puts that many cards from the top of his or her library into his or her graveyard";
    }

    SzadekLordOfSecretsEffect(final SzadekLordOfSecretsEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Player damagedPlayer = game.getPlayer(damageEvent.getTargetId());

        if (damageEvent.isCombatDamage()) {
            Permanent p = game.getPermanent(source.getSourceId());
            if (p != null) {
                p.addCounters(CounterType.P1P1.createInstance(damageEvent.getAmount()), game);
                if (damagedPlayer != null) {
                    cards = damagedPlayer.getLibrary().getTopCards(game, damageEvent.getAmount());
                }
                for (Card card : cards) {
                    card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, false);
                }
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                && event.getSourceId().equals(source.getSourceId())) {
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
