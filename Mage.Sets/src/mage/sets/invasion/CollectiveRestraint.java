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
package mage.sets.invasion;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostImpl;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayManaAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Simown
 */
public class CollectiveRestraint extends CardImpl {

    public CollectiveRestraint(UUID ownerId) {
        super(ownerId, 49, "Collective Restraint", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        this.expansionSetCode = "INV";

        // Domain - Creatures can't attack you unless their controller pays {X} for each creature he or she controls that's attacking you, where X is the number of basic land types you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CollectiveRestraintPayManaToAttackAllEffect()));

    }

    public CollectiveRestraint(final CollectiveRestraint card) {
        super(card);
    }

    @Override
    public CollectiveRestraint copy() {
        return new CollectiveRestraint(this);
    }
}

class CollectiveRestraintPayManaToAttackAllEffect extends CantAttackYouUnlessPayManaAllEffect {

    CollectiveRestraintPayManaToAttackAllEffect() {
        super(null, true);
        staticText = "Creatures can't attack you unless their controller pays {X} for each creature he or she controls that's attacking you, where X is the number of basic land types you control.";
    }

    CollectiveRestraintPayManaToAttackAllEffect(CollectiveRestraintPayManaToAttackAllEffect effect) {
        super(effect);
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        int domainValue = new DomainValue().calculate(game, source, this);
        if (domainValue > 0) {
            return new ManaCostsImpl<>("{" + domainValue + "}");
        }
        return null;
    }

    @Override
    public CollectiveRestraintPayManaToAttackAllEffect copy() {
        return new CollectiveRestraintPayManaToAttackAllEffect(this);
    }

}
