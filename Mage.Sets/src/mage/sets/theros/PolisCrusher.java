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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class PolisCrusher extends CardImpl<PolisCrusher> {

    public PolisCrusher(UUID ownerId) {
        super(ownerId, 198, "Polis Crusher", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        this.expansionSetCode = "THS";
        this.subtype.add("Cyclops");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // protection from enchantments
        this.addAbility(new ProtectionAbility(new FilterEnchantmentPermanent()));
        // {4}{R}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{4}{R}{G}", 3));
        // Whenever Polis Crusher deals combat damage to a player, if Polis Crusher is monstrous, destroy target enchantment that player controls.
        Ability ability = new ConditionalTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(new PolisCrusherDestroyEffect(), false, true), 
                MonstrousCondition.getInstance(),
                "Whenever {this} deals combat damage to a player, if {this} is monstrous, destroy target enchantment that player controls.");
        this.addAbility(ability);
    }

    public PolisCrusher(final PolisCrusher card) {
        super(card);
    }

    @Override
    public PolisCrusher copy() {
        return new PolisCrusher(this);
    }
}

class PolisCrusherDestroyEffect extends OneShotEffect<PolisCrusherDestroyEffect> {

    public PolisCrusherDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy target enchantment that player controls";
    }

    public PolisCrusherDestroyEffect(final PolisCrusherDestroyEffect effect) {
        super(effect);
    }

    @Override
    public PolisCrusherDestroyEffect copy() {
        return new PolisCrusherDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player attackedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && attackedPlayer != null) {
            FilterPermanent filter = new FilterEnchantmentPermanent("enchantment attacked player controls");
            filter.add(new ControllerIdPredicate(attackedPlayer.getId()));
            Target target = new TargetPermanent(filter);
            target.setRequired(true);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Permanent enchantment = game.getPermanent(target.getFirstTarget());
                if (enchantment != null) {
                    return enchantment.destroy(source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }
}
