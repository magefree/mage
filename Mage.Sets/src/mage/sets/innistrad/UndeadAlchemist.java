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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class UndeadAlchemist extends CardImpl<UndeadAlchemist> {

    public UndeadAlchemist(UUID ownerId) {
        super(ownerId, 84, "Undead Alchemist", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Zombie");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // If a Zombie you control would deal combat damage to a player, instead that player puts that many cards from the top of his or her library into his or her graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UndeadAlchemistEffect()));

        // Whenever a creature card is put into an opponent's graveyard from his or her library, exile that card and put a 2/2 black Zombie creature token onto the battlefield.
        this.addAbility(new UndeadAlchemistTriggeredAbility());
    }

    public UndeadAlchemist(final UndeadAlchemist card) {
        super(card);
    }

    @Override
    public UndeadAlchemist copy() {
        return new UndeadAlchemist(this);
    }
}

class UndeadAlchemistTriggeredAbility extends TriggeredAbilityImpl<UndeadAlchemistTriggeredAbility> {

    public UndeadAlchemistTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new ExileTargetEffect(), true);
        this.addEffect(new CreateTokenEffect(new ZombieToken()));
    }

    public UndeadAlchemistTriggeredAbility(final UndeadAlchemistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UndeadAlchemistTriggeredAbility copy() {
        return new UndeadAlchemistTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Constants.Zone.LIBRARY && zEvent.getToZone() == Constants.Zone.GRAVEYARD && game.getOpponents(this.getControllerId()).contains(zEvent.getPlayerId())) {
                Card card = game.getCard(event.getTargetId());
                if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(card.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature card is put into an opponent's graveyard from his or her library, exile that card and put a 2/2 black Zombie creature token onto the battlefield.";
    }
}

class UndeadAlchemistEffect extends ReplacementEffectImpl<UndeadAlchemistEffect> {

    UndeadAlchemistEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.RedirectDamage);
        staticText = "If a Zombie you control would deal combat damage to a player, instead that player puts that many cards from the top of his or her library into his or her graveyard";
    }

    UndeadAlchemistEffect(final UndeadAlchemistEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        if (player != null) {
            int cardsCount = Math.min(event.getAmount(), player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null)
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                else
                    break;
            }

            return true;
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER && event instanceof DamagePlayerEvent) {
            DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
            if (damageEvent.isCombatDamage()) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null && permanent.getSubtype().contains("Zombie"))
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
    public UndeadAlchemistEffect copy() {
        return new UndeadAlchemistEffect(this);
    }
}
