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
package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author emerald000
 */
public class RashmiEternitiesCrafter extends CardImpl {

    public RashmiEternitiesCrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{U}");
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Druid");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast your first spell each turn, reveal the top card of your library. If it's a nonland card with converted mana cost less than that spell's, you may cast it without paying its mana cost. If you don't cast the revealed card, put it into your hand.
        this.addAbility(new RashmiEternitiesCrafterTriggeredAbility(), new SpellsCastWatcher());
    }

    public RashmiEternitiesCrafter(final RashmiEternitiesCrafter card) {
        super(card);
    }

    @Override
    public RashmiEternitiesCrafter copy() {
        return new RashmiEternitiesCrafter(this);
    }
}

class RashmiEternitiesCrafterTriggeredAbility extends SpellCastControllerTriggeredAbility {

    RashmiEternitiesCrafterTriggeredAbility() {
        super(new RashmiEternitiesCrafterEffect(), false);
    }

    RashmiEternitiesCrafterTriggeredAbility(RashmiEternitiesCrafterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RashmiEternitiesCrafterTriggeredAbility copy() {
        return new RashmiEternitiesCrafterTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getName());
            if (watcher != null) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
                if (spells != null && spells.size() == 1) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    if (spell != null) {
                        for (Effect effect : getEffects()) {
                            effect.setValue("RashmiEternitiesCrafterCMC", spell.getConvertedManaCost());
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first spell each turn, reveal the top card of your library. If it's a nonland card with converted mana cost less than that spell's, you may cast it without paying its mana cost. If you don't cast the revealed card, put it into your hand.";
    }
}

class RashmiEternitiesCrafterEffect extends OneShotEffect {

    RashmiEternitiesCrafterEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "reveal the top card of your library. If it's a nonland card with converted mana cost less than that spell's, you may cast it without paying its mana cost. If you don't cast the revealed card, put it into your hand";
    }

    RashmiEternitiesCrafterEffect(final RashmiEternitiesCrafterEffect effect) {
        super(effect);
    }

    @Override
    public RashmiEternitiesCrafterEffect copy() {
        return new RashmiEternitiesCrafterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards("Rashmi, Eternities Crafter", new CardsImpl(card), game);
                Object cmcObject = this.getValue("RashmiEternitiesCrafterCMC");
                if (cmcObject == null
                        || card.isLand()
                        || card.getConvertedManaCost() >= (int) cmcObject
                        || !controller.chooseUse(Outcome.PlayForFree, "Cast " + card.getName() + " without paying its mana cost?", source, game)
                        || !controller.cast(card.getSpellAbility(), game, true)) {
                    controller.moveCards(card, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
