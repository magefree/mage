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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class RaggedVeins extends CardImpl<RaggedVeins> {

    public RaggedVeins(UUID ownerId) {
        super(ownerId, 139, "Ragged Veins", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Aura");
        this.color.setBlack(true);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted creature is dealt damage, its controller loses that much life.
        this.addAbility(new RaggedVeinsTriggeredAbility());
    }

    public RaggedVeins(final RaggedVeins card) {
        super(card);
    }

    @Override
    public RaggedVeins copy() {
        return new RaggedVeins(this);
    }
}

class RaggedVeinsTriggeredAbility extends TriggeredAbilityImpl<RaggedVeinsTriggeredAbility> {

    public RaggedVeinsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RaggedVeinsEffect());
    }

    public RaggedVeinsTriggeredAbility(final RaggedVeinsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RaggedVeinsTriggeredAbility copy() {
        return new RaggedVeinsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE) {
            Permanent enchantment = game.getPermanent(sourceId);
            UUID targetId = event.getTargetId();
            if (enchantment != null && enchantment.getAttachedTo() != null && targetId.equals(enchantment.getAttachedTo())) {
                this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                this.getEffects().get(0).setTargetPointer(new FixedTarget(targetId));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature is dealt damage, its controller loses that much life.";
    }
}

class  RaggedVeinsEffect extends OneShotEffect<RaggedVeinsEffect> {

    public RaggedVeinsEffect() {
        super(Outcome.Damage);
        this.staticText = "its controller loses that much life";
    }

    public RaggedVeinsEffect(final RaggedVeinsEffect effect) {
        super(effect);
    }

    @Override
    public RaggedVeinsEffect copy() {
        return new RaggedVeinsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damageAmount = (Integer) this.getValue("damageAmount");
        UUID targetId = this.targetPointer.getFirst(game, source);
        if (damageAmount != null && targetId != null) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                permanent = (Permanent) game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
            }
            if (permanent != null) {
                Player player = game.getPlayer(permanent.getControllerId());
                if (player != null) {
                    player.loseLife(damageAmount, game);
                    return true;
                }
            }
        }
        return false;
    }
}