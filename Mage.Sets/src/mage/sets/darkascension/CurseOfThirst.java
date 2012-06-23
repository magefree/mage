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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class CurseOfThirst extends CardImpl<CurseOfThirst> {

    public CurseOfThirst(UUID ownerId) {
        super(ownerId, 57, "Curse of Thirst", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Aura");
        this.subtype.add("Curse");

        this.color.setBlack(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // At the beginning of enchanted player's upkeep, Curse of Thirst deals damage to that player equal to the number of Curses attached to him or her.
        this.addAbility(new CurseOfThirstAbility());

    }

    public CurseOfThirst(final CurseOfThirst card) {
        super(card);
    }

    @Override
    public CurseOfThirst copy() {
        return new CurseOfThirst(this);
    }
}

class CurseOfThirstAbility extends TriggeredAbilityImpl<CurseOfThirstAbility> {

    public CurseOfThirstAbility() {
        super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(new CursesAttachedCount()));
    }

    public CurseOfThirstAbility(final CurseOfThirstAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfThirstAbility copy() {
        return new CurseOfThirstAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            Permanent enchantment = game.getPermanent(this.sourceId);
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Player player = game.getPlayer(enchantment.getAttachedTo());
                if (player != null && game.getActivePlayerId().equals(player.getId())) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, Curse of Thirst deals damage to that player equal to the number of Curses attached to him or her.";
    }

}

class CursesAttachedCount implements DynamicValue {

    public CursesAttachedCount() {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int count = 0;
        Permanent enchantment = game.getPermanent(sourceAbility.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player player = game.getPlayer(enchantment.getAttachedTo());
            if (player != null) {
                for (UUID attachmentId: player.getAttachments()) {
                    Permanent attachment = game.getPermanent(attachmentId);
                    if (attachment != null && attachment.getSubtype().contains("Curse"))
                        count++;
                }
            }
        }
        return count;
    }

    @Override
    public DynamicValue clone() {
        return new CursesAttachedCount();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "number of Curses attached to him or her";
    }
}
