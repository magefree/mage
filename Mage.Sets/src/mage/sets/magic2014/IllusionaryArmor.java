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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class IllusionaryArmor extends CardImpl<IllusionaryArmor> {

    public IllusionaryArmor(UUID ownerId) {
        super(ownerId, 59, "Illusionary Armor", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.expansionSetCode = "M14";
        this.subtype.add("Aura");

        this.color.setBlue(true);

        // Enchant Creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        // Enchanted creature gets +4/+4.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(4, 4, Duration.WhileOnBattlefield)));
        // When enchanted creature becomes the target of a spell or ability, sacrifice Illusionary Armor.
        this.addAbility(new IllusionaryArmorAbility());
    }

    public IllusionaryArmor(final IllusionaryArmor card) {
        super(card);
    }

    @Override
    public IllusionaryArmor copy() {
        return new IllusionaryArmor(this);
    }
}


class IllusionaryArmorAbility extends TriggeredAbilityImpl<IllusionaryArmorAbility> {

    public IllusionaryArmorAbility() {
        super(Zone.BATTLEFIELD, new DestroySourceEffect());
    }

    public IllusionaryArmorAbility(final IllusionaryArmorAbility ability) {
        super(ability);
    }

    @Override
    public IllusionaryArmorAbility copy() {
        return new IllusionaryArmorAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TARGETED) {
            Permanent enchantment = game.getPermanent(sourceId);
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                if (event.getTargetId().equals(enchantment.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted creature becomes the target of a spell or ability, destroy {this}.";
    }

}
