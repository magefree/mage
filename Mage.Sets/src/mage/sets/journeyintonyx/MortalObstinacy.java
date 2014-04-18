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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public class MortalObstinacy extends CardImpl<MortalObstinacy> {

    public MortalObstinacy(UUID ownerId) {
        super(ownerId, 17, "Mortal Obstinacy", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1,1, Duration.WhileOnBattlefield)));
        // Whenever enchanted creature deals combat damage to a player, you may sacrifice Mortal Obstinacy. If you do, destroy target enchantment.
        this.addAbility(new MortalObstinacyAbility());

    }

    public MortalObstinacy(final MortalObstinacy card) {
        super(card);
    }

    @Override
    public MortalObstinacy copy() {
        return new MortalObstinacy(this);
    }
}

class MortalObstinacyAbility extends TriggeredAbilityImpl<MortalObstinacyAbility> {

    public MortalObstinacyAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
    }

    public MortalObstinacyAbility(final MortalObstinacyAbility ability) {
        super(ability);
    }

    @Override
    public MortalObstinacyAbility copy() {
        return new MortalObstinacyAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent damageMakingCreature = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && damageMakingCreature != null && damageMakingCreature.getAttachments().contains(this.getSourceId())) {
                Player controller = game.getPlayer(this.getControllerId());
                Permanent sourceEnchantment = game.getPermanent(this.getSourceId());
                if (controller != null && sourceEnchantment != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Do you wish to sacrifice ").append(sourceEnchantment.getName());
                    sb.append(" to destroy target enchantment?");
                    if (controller.chooseUse(Outcome.DestroyPermanent, sb.toString(), game)) {
                        this.getTargets().clear();
                        this.addTarget(new TargetEnchantmentPermanent(true));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature deals combat damage to a player, you may sacrifice {this}. If you do, destroy target enchantment.";
    }
}