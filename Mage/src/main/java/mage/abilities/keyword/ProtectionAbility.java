/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.StaticAbility;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ProtectionAbility extends StaticAbility {

    protected Filter filter;
    protected boolean removeAuras;
    protected UUID auraIdNotToBeRemoved; // defines an Aura objectId that will not be removed from this protection ability

    public ProtectionAbility(Filter filter) {
        super(Zone.BATTLEFIELD, null);
        this.filter = filter;
        this.removeAuras = true;
        this.auraIdNotToBeRemoved = null;
    }

    public ProtectionAbility(final ProtectionAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.removeAuras = ability.removeAuras;
        this.auraIdNotToBeRemoved = ability.auraIdNotToBeRemoved;
    }

    @Override
    public ProtectionAbility copy() {
        return new ProtectionAbility(this);
    }

    @Override
    public String getRule() {

        return "Protection from " + filter.getMessage() + (removeAuras ? "" : ". This effect doesn't remove auras.");
    }

    public boolean canTarget(MageObject source, Game game) {
        if (filter instanceof FilterPermanent) {
            if (source instanceof Permanent) {
                return !filter.match(source, game);
            }
            return true;
        }

        if (filter instanceof FilterCard) {
            if (source instanceof Card) {
                return !filter.match(source, game);
            }
            return true;
        }
        if (filter instanceof FilterSpell) {
            if (source instanceof Spell) {
                return !filter.match(source, game);
            }
            // Problem here is that for the check if a player can play a Spell, the source
            // object is still a card and not a spell yet. So retunr only if the source object can't be a spell
            // otherwise the following FilterObject check will be appied
            if (source instanceof StackObject
                    || (!source.getCardType().contains(CardType.INSTANT) && !source.getCardType().contains(CardType.SORCERY))) {
                return true;
            }
        }
        if (filter instanceof FilterObject) {
            return !filter.match(source, game);
        }
        return true;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(FilterCard filter) {
        this.filter = filter;
    }

    public void setRemovesAuras(boolean removeAuras) {
        this.removeAuras = removeAuras;
    }

    public boolean removesAuras() {
        return removeAuras;
    }

    public UUID getAuraIdNotToBeRemoved() {
        return auraIdNotToBeRemoved;
    }

    public void setAuraIdNotToBeRemoved(UUID auraIdNotToBeRemoved) {
        this.auraIdNotToBeRemoved = auraIdNotToBeRemoved;
    }

}
