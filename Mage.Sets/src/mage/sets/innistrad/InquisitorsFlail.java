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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class InquisitorsFlail extends CardImpl<InquisitorsFlail> {

    public InquisitorsFlail(UUID ownerId) {
        super(ownerId, 227, "Inquisitor's Flail", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Equipment");

        // If equipped creature would deal combat damage, it deals double that damage instead.
        // If another creature would deal combat damage to equipped creature, it deals double that damage to equipped creature instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new InquisitorsFlailEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(2)));
    }

    public InquisitorsFlail(final InquisitorsFlail card) {
        super(card);
    }

    @Override
    public InquisitorsFlail copy() {
        return new InquisitorsFlail(this);
    }
}

class InquisitorsFlailEffect extends ReplacementEffectImpl<InquisitorsFlailEffect> {

    public InquisitorsFlailEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Damage);
        staticText = "If equipped creature would deal combat damage, it deals double that damage instead. \n"
                + "If another creature would deal combat damage to equipped creature, it deals double that damage to equipped creature instead";
    }

    public InquisitorsFlailEffect(final InquisitorsFlailEffect effect) {
        super(effect);
    }

    @Override
    public InquisitorsFlailEffect copy() {
        return new InquisitorsFlailEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                if (((DamagedEvent) event).isCombatDamage()) {
                    Permanent equipment = game.getPermanent(source.getSourceId());
                    if (equipment != null && equipment.getAttachedTo() != null) {
                        UUID attachedTo = equipment.getAttachedTo();
                        if (event.getSourceId().equals(attachedTo)) {
                            event.setAmount(event.getAmount() * 2);
                        } else if (event.getTargetId().equals(attachedTo)) {
                            event.setAmount(event.getAmount() * 2);
                        }
                        return true;
                    }
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

}
