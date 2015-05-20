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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class SwansOfBrynArgoll extends CardImpl {

    public SwansOfBrynArgoll(UUID ownerId) {
        super(ownerId, 151, "Swans of Bryn Argoll", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W/U}{W/U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Bird");
        this.subtype.add("Spirit");

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If a source would deal damage to Swans of Bryn Argoll, prevent that damage. The source's controller draws cards equal to the damage prevented this way.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SwansOfBrynArgollEffect()));

    }

    public SwansOfBrynArgoll(final SwansOfBrynArgoll card) {
        super(card);
    }

    @Override
    public SwansOfBrynArgoll copy() {
        return new SwansOfBrynArgoll(this);
    }
}

class SwansOfBrynArgollEffect extends PreventionEffectImpl {

    SwansOfBrynArgollEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If a source would deal damage to {this}, prevent that damage. The source's controller draws cards equal to the damage prevented this way";
    }

    SwansOfBrynArgollEffect(final SwansOfBrynArgollEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        if (preventionEffectData.getPreventedDamage() > 0) {
            Boolean passed = false;
            MageObject sourceOfDamage = game.getObject(event.getSourceId());
            if (sourceOfDamage != null) {
                Spell spell = game.getStack().getSpell(sourceOfDamage.getId());
                Permanent permanent = game.getPermanentOrLKIBattlefield(sourceOfDamage.getId());
                if (spell != null) {
                    Player controllerOfSpell = game.getPlayer(spell.getControllerId());
                    controllerOfSpell.drawCards(preventionEffectData.getPreventedDamage(), game);
                    passed = true;
                }
                if (permanent != null) {
                    Player controllerOfPermanent = game.getPlayer(permanent.getControllerId());
                    controllerOfPermanent.drawCards(preventionEffectData.getPreventedDamage(), game);
                    passed = true;
                }
                if (!passed) {
                    // Needed for cards that do damage from hand e.g. Gempalm Incinerator
                    Card cardSource = game.getCard(event.getSourceId());
                    if (cardSource != null) {
                        Player owner = game.getPlayer(cardSource.getOwnerId());
                        if (owner != null) {
                            owner.drawCards(preventionEffectData.getPreventedDamage(), game);
                        }
                    }
                }
            }
        }
        return preventionEffectData.isReplaced();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == EventType.DAMAGE_CREATURE
                && event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SwansOfBrynArgollEffect copy() {
        return new SwansOfBrynArgollEffect(this);
    }
}
