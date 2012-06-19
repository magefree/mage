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
package mage.sets.riseoftheeldrazi;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 
 * @author Rafbill
 */
public class NotOfThisWorld extends CardImpl<NotOfThisWorld> {

    public NotOfThisWorld(UUID ownerId) {
        super(ownerId, 8, "Not of This World", Rarity.UNCOMMON,
                new CardType[] { CardType.INSTANT }, "{7}");
        this.expansionSetCode = "ROE";
        this.supertype.add("Tribal");
        this.subtype.add("Eldrazi");

        // Counter target spell or ability that targets a permanent you control.
        this.getSpellAbility()
                .addTarget(
                        new TargetSpellTargetingControlledPermanent(
                                new FilterSpell(
                                        "a spell or ability that targets a creature you control with power 7 or greater.")));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        // Not of This World costs {7} less to cast if it targets a spell or
        // ability that targets a creature you control with power 7 or greater.
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {

        if (ability.getTargets().isChosen()
                && game.getStack().getStackObject(
                        ability.getTargets().getFirstTarget()) != null
                && game.getPermanent(game.getStack()
                        .getStackObject(ability.getTargets().getFirstTarget())
                        .getStackAbility().getTargets().getFirstTarget()) != null
                && game.getPermanent(
                        game.getStack()
                                .getStackObject(
                                        ability.getTargets().getFirstTarget())
                                .getStackAbility().getTargets()
                                .getFirstTarget()).getPower().getValue() >= 7) {

            String adjustedCost = "{0}";
            ability.getManaCostsToPay().clear();
            ability.getManaCostsToPay().load(adjustedCost);

        }

    }

    public NotOfThisWorld(final NotOfThisWorld card) {
        super(card);
    }

    @Override
    public NotOfThisWorld copy() {
        return new NotOfThisWorld(this);
    }
}

class TargetSpellTargetingControlledPermanent extends
        TargetObject<TargetSpellTargetingControlledPermanent> {

    protected FilterSpell filter;

    public TargetSpellTargetingControlledPermanent() {
        this(1, 1, new FilterSpell());
    }

    public TargetSpellTargetingControlledPermanent(FilterSpell filter) {
        this(1, 1, filter);
    }

    public TargetSpellTargetingControlledPermanent(int numTargets,
            FilterSpell filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetSpellTargetingControlledPermanent(int minNumTargets,
            int maxNumTargets, FilterSpell filter) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = Zone.STACK;
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    public TargetSpellTargetingControlledPermanent(
            final TargetSpellTargetingControlledPermanent target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public FilterSpell getFilter() {
        return filter;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(id);
        if (spell != null) {
            return filter.match(spell, game);
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        return canChoose(sourceControllerId, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell
                    && game.getPlayer(sourceControllerId).getInRange()
                            .contains(stackObject.getControllerId())
                    && filter.match((Spell) stackObject, game)
                    && ((Spell) stackObject).getSpellAbility().getTargets()
                            .isChosen()
                    && game.getPermanent(((Spell) stackObject)
                            .getSpellAbility().getTargets().getFirstTarget()) != null
                    && game.getPermanent(
                            ((Spell) stackObject).getSpellAbility()
                                    .getTargets().getFirstTarget())
                            .getControllerId() == sourceControllerId) {
                count++;
                if (count >= this.minNumberOfTargets)
                    return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId,
            Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<UUID>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell
                    && game.getPlayer(sourceControllerId).getInRange()
                            .contains(stackObject.getControllerId())
                    && filter.match((Spell) stackObject, game)
                    && ((Spell) stackObject).getSpellAbility().getTargets()
                            .isChosen()
                    && game.getPermanent(((Spell) stackObject)
                            .getSpellAbility().getTargets().getFirstTarget()) != null
                    && game.getPermanent(
                            ((Spell) stackObject).getSpellAbility()
                                    .getTargets().getFirstTarget())
                            .getControllerId() == sourceControllerId) {
                possibleTargets.add(stackObject.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetSpellTargetingControlledPermanent copy() {
        return new TargetSpellTargetingControlledPermanent(this);
    }

}
