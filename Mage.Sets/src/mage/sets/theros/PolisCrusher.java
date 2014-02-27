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
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author LevelX2
 */
public class PolisCrusher extends CardImpl<PolisCrusher> {

    private static final FilterCard filter = new FilterCard("enchantments");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

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
        this.addAbility(new ProtectionAbility(filter));
        // {4}{R}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{4}{R}{G}", 3));
        // Whenever Polis Crusher deals combat damage to a player, if Polis Crusher is monstrous, destroy target enchantment that player controls.
        Ability ability = new ConditionalTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect(), false, true),
                MonstrousCondition.getInstance(),
                "Whenever {this} deals combat damage to a player, if {this} is monstrous, destroy target enchantment that player controls.");
        this.addAbility(ability);
    }

    public PolisCrusher(final PolisCrusher card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof ConditionalTriggeredAbility) {
            for (Effect effect : ability.getEffects()) {
                if (effect instanceof DestroyTargetEffect) {
                    Player attackedPlayer = game.getPlayer(effect.getTargetPointer().getFirst(game, ability));
                    if (attackedPlayer != null) {
                        ability.getTargets().clear();
                        FilterPermanent filterEnchantment = new FilterEnchantmentPermanent("enchantment attacked player controls");
                        filter.add(new ControllerIdPredicate(attackedPlayer.getId()));
                        Target target = new TargetPermanent(filterEnchantment);
                        target.setRequired(true);
                        ability.addTarget(target);
                        effect.setTargetPointer(new FirstTargetPointer());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public PolisCrusher copy() {
        return new PolisCrusher(this);
    }
}
