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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public class ElbrusTheBindingBlade extends CardImpl<ElbrusTheBindingBlade> {

    public ElbrusTheBindingBlade(UUID ownerId) {
        super(ownerId, 147, "Elbrus, the Binding Blade", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{7}");
        this.expansionSetCode = "DKA";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");
        
        this.canTransform = true;
        this.secondSideCard = new WithengarUnbound(ownerId);
        this.addAbility(new TransformAbility());

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        // When equipped creature deals combat damage to a player, unattach Elbrus, the Binding Blade, then transform it.
         this.addAbility(new DealsCombatDamageToAPlayerAttachedTriggeredAbility(new ElbrusTheBindingBladeEffect(), "equipped", true));
       // Equip {1}
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(1)));
    }

    public ElbrusTheBindingBlade(final ElbrusTheBindingBlade card) {
        super(card);
    }

    @Override
    public ElbrusTheBindingBlade copy() {
        return new ElbrusTheBindingBlade(this);
    }
}

class ElbrusTheBindingBladeEffect extends OneShotEffect<ElbrusTheBindingBladeEffect> {
    public ElbrusTheBindingBladeEffect() {
        super(Constants.Outcome.BecomeCreature);
        staticText = "unattach {this}, then transform it";
    }

    public ElbrusTheBindingBladeEffect(final ElbrusTheBindingBladeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(equipment.getAttachedTo());
            if (attachedTo != null) {
                attachedTo.removeAttachment(equipment.getId(), game);
                equipment.transform(game);
            }
        }
        return false;
    }

    @Override
    public ElbrusTheBindingBladeEffect copy() {
        return new ElbrusTheBindingBladeEffect(this);
    }

}