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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AuraBarbs extends CardImpl<AuraBarbs> {

    public AuraBarbs(UUID ownerId) {
        super(ownerId, 94, "Aura Barbs", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Arcane");
        this.color.setRed(true);

        // Each enchantment deals 2 damage to its controller, then each Aura attached to a creature deals 2 damage to the creature it's attached to.
        this.getSpellAbility().addEffect(new AuraBarbsEffect());
    }

    public AuraBarbs(final AuraBarbs card) {
        super(card);
    }

    @Override
    public AuraBarbs copy() {
        return new AuraBarbs(this);
    }

    private class AuraBarbsEffect extends OneShotEffect<AuraBarbsEffect> {

    public AuraBarbsEffect() {
            super(Constants.Outcome.Detriment);
            staticText = "Each enchantment deals 2 damage to its controller, then each Aura attached to a creature deals 2 damage to the creature it's attached to";
        }

        public AuraBarbsEffect(final AuraBarbsEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {

            FilterPermanent filterEnchantments = new FilterPermanent();
            filterEnchantments.add(new CardTypePredicate(CardType.ENCHANTMENT));

            for (Permanent permanent : game.getBattlefield().getActivePermanents(filterEnchantments, source.getControllerId(), source.getSourceId(), game)) {
                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null) {
                    controller.damage(2, permanent.getId(), game, false, true);
                    game.informPlayers("2 damage assigned to " + controller.getName() + " from " + permanent.getName());
                }
            }

            filterEnchantments.add(new SubtypePredicate("Aura"));
            for (Permanent auraEnchantment : game.getBattlefield().getActivePermanents(filterEnchantments, source.getControllerId(), source.getSourceId(), game)) {
                if (auraEnchantment.getAttachedTo() != null) {
                    Permanent attachedToCreature = game.getPermanent(auraEnchantment.getAttachedTo());
                    if (attachedToCreature != null && attachedToCreature.getCardType().contains(CardType.CREATURE)) {
                        attachedToCreature.damage(2, auraEnchantment.getId(), game, true, false);
                        game.informPlayers("2 damage assigned to " + attachedToCreature.getName() + " from " + auraEnchantment.getName());
                    }
                }
            }
            return true;
        }

        @Override
        public AuraBarbsEffect copy() {
            return new AuraBarbsEffect(this);
        }

    }
}

