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
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class MarkOfSakiko extends CardImpl {

    public MarkOfSakiko(UUID ownerId) {
        super(ownerId, 135, "Mark of Sakiko", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature has "Whenever this creature deals combat damage to a player, add that much {G} to your mana pool. Until end of turn, this mana doesn't empty from your mana pool as steps and phases end."
        Effect effect  = new GainAbilityAttachedEffect(new MarkOfSakikoTriggeredAbility(), AttachmentType.AURA);
        effect.setText("Enchanted creature has \"Whenever this creature deals combat damage to a player, add that much {G} to your mana pool. Until end of turn, this mana doesn't empty from your mana pool as steps and phases end.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
    }

    public MarkOfSakiko(final MarkOfSakiko card) {
        super(card);
    }

    @Override
    public MarkOfSakiko copy() {
        return new MarkOfSakiko(this);
    }
}

class MarkOfSakikoTriggeredAbility extends TriggeredAbilityImpl {

    public MarkOfSakikoTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public MarkOfSakikoTriggeredAbility(final MarkOfSakikoTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarkOfSakikoTriggeredAbility copy() {
        return new MarkOfSakikoTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                if (event.getSourceId().equals(getSourceId())) {
                    this.getEffects().clear();
                    Effect effect = new AddManaToManaPoolTargetControllerEffect(new Mana(0,event.getAmount(),0,0,0,0,0), "that player", true);
                    effect.setTargetPointer(new FixedTarget(getControllerId()));
                    effect.setText("add that much {G} to your mana pool. Until end of turn, this mana doesn't empty from your mana pool as steps and phases end");        
                    this.addEffect(effect);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals combat damage to a player, add that much {G} to your mana pool. Until end of turn, this mana doesn't empty from your mana pool as steps and phases end.";
    }
}