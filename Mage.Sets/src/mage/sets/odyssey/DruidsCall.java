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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SquirrelToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DruidsCall extends CardImpl<DruidsCall> {

    public DruidsCall(UUID ownerId) {
        super(ownerId, 239, "Druid's Call", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature is dealt damage, its controller puts that many 1/1 green Squirrel creature tokens onto the battlefield.
        this.addAbility(new DruidsCallTriggeredAbility());
    }

    public DruidsCall(final DruidsCall card) {
        super(card);
    }

    @Override
    public DruidsCall copy() {
        return new DruidsCall(this);
    }
}

class DruidsCallTriggeredAbility extends TriggeredAbilityImpl<DruidsCallTriggeredAbility> {

    public DruidsCallTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DruidsCallEffect());
    }

    public DruidsCallTriggeredAbility(final DruidsCallTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DruidsCallTriggeredAbility copy() {
        return new DruidsCallTriggeredAbility(this);
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
        return "Whenever enchanted creature is dealt damage, its controller puts that many 1/1 green Squirrel creature tokens onto the battlefield.";
    }
}

class  DruidsCallEffect extends OneShotEffect<DruidsCallEffect> {

    public DruidsCallEffect() {
        super(Outcome.Damage);
        this.staticText = "its controller puts that many 1/1 green Squirrel creature tokens onto the battlefield";
    }

    public DruidsCallEffect(final DruidsCallEffect effect) {
        super(effect);
    }

    @Override
    public DruidsCallEffect copy() {
        return new DruidsCallEffect(this);
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
                    Token token = new SquirrelToken();
                    token.putOntoBattlefield(damageAmount, game, source.getSourceId(), player.getId());
                    return true;
                }
            }
        }
        return false;
    }
}
