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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.StaticAbility;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
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
    protected static List<ObjectColor> colors = new ArrayList<>();
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

    public static ProtectionAbility from(ObjectColor color) {
        FilterObject filter = new FilterObject(color.getDescription());
        filter.add(new ColorPredicate(color));
        colors.add(color);
        return new ProtectionAbility(filter);
    }

    public static ProtectionAbility from(ObjectColor color1, ObjectColor color2) {
        FilterObject filter = new FilterObject(color1.getDescription() + " and from " + color2.getDescription());
        filter.add(Predicates.or(new ColorPredicate(color1), new ColorPredicate(color2)));
        colors.add(color1);
        colors.add(color2);
        return new ProtectionAbility(filter);
    }

    @Override
    public ProtectionAbility copy() {
        return new ProtectionAbility(this);
    }

    @Override
    public String getRule() {

        return "protection from " + filter.getMessage() + (removeAuras ? "" : ". This effect doesn't remove auras.");
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
            // object is still a card and not a spell yet. So return only if the source object can't be a spell
            // otherwise the following FilterObject check will be applied
            if (source instanceof StackObject
                    || (!source.isInstant() && !source.isSorcery())) {
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

    public List<ObjectColor> getColors() { return colors; }

    public UUID getAuraIdNotToBeRemoved() {
        return auraIdNotToBeRemoved;
    }

    public void setAuraIdNotToBeRemoved(UUID auraIdNotToBeRemoved) {
        this.auraIdNotToBeRemoved = auraIdNotToBeRemoved;
    }

}
