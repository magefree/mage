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
package mage.sets.magic2013;

import mage.Constants.*;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author North
 */
public class Omniscience extends CardImpl<Omniscience> {

    public Omniscience(UUID ownerId) {
        super(ownerId, 63, "Omniscience", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{7}{U}{U}{U}");
        this.expansionSetCode = "M13";

        this.color.setBlue(true);

        // You may cast nonland cards from your hand without paying their mana costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OmniscienceEffect()));
    }

    public Omniscience(final Omniscience card) {
        super(card);
    }

    @Override
    public Omniscience copy() {
        return new Omniscience(this);
    }
}

class OmniscienceEffect extends CostModificationEffectImpl<OmniscienceEffect> {

    public OmniscienceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PlayForFree);
        this.staticText = "You may cast nonland cards from your hand without paying their mana costs";
    }

    private OmniscienceEffect(final OmniscienceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        spellAbility.getManaCostsToPay().clear();
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility || abilityToModify instanceof RetraceAbility) {
            Card sourceCard = game.getCard(abilityToModify.getSourceId());
            StackObject stackObject = game.getStack().getStackObject(abilityToModify.getSourceId());
            if (stackObject != null && stackObject instanceof Spell) {
                Zone zone = ((Spell)stackObject).getFromZone();
                if (zone != null && zone.equals(Zone.HAND)) {
                    if (sourceCard != null && sourceCard.getOwnerId().equals(source.getControllerId())
                            && !sourceCard.getCardType().contains(CardType.LAND)) {
                        Player player = game.getPlayer(source.getControllerId());
                        String message = "Cast " + sourceCard.getName() + " without paying its mana costs?";
                        if (player != null && player.chooseUse(outcome, message, game)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public OmniscienceEffect copy() {
        return new OmniscienceEffect(this);
    }
}
