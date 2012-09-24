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

package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continious.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class MarkOfTheOni extends CardImpl<MarkOfTheOni> {

    public MarkOfTheOni(UUID ownerId) {
        super(ownerId, 73, "Mark of the Oni", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.expansionSetCode = "BOK";
        this.color.setBlack(true);
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));

        // At the beginning of the end step, if you control no Demons, sacrifice Mark of the Oni.
        this.addAbility(new MarkOfTheOniTriggeredAbility());

    }

    public MarkOfTheOni(final MarkOfTheOni card) {
        super(card);
    }

    @Override
    public MarkOfTheOni copy() {
        return new MarkOfTheOni(this);
    }

    private class MarkOfTheOniTriggeredAbility extends TriggeredAbilityImpl<MarkOfTheOniTriggeredAbility> {
        MarkOfTheOniTriggeredAbility() {
            super(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect());
        }

        MarkOfTheOniTriggeredAbility(final MarkOfTheOniTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public MarkOfTheOniTriggeredAbility copy() {
            return new MarkOfTheOniTriggeredAbility(this);
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent();
                filter.add(new SubtypePredicate("Demon"));
                if (!game.getBattlefield().contains(filter, controllerId, 1, game)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "At the beginning of the end step, if you control no Demons, sacrifice {this}.";
        }
    }
}

