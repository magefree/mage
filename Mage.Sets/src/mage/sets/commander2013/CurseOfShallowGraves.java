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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class CurseOfShallowGraves extends CardImpl<CurseOfShallowGraves> {

    public CurseOfShallowGraves(UUID ownerId) {
        super(ownerId, 71, "Curse of Shallow Graves", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.expansionSetCode = "C13";
        this.subtype.add("Aura");
        this.subtype.add("Curse");

        this.color.setBlack(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever a player attacks enchanted player with one or more creatures, that attacking player may put a 2/2 black Zombie creature token onto the battlefield tapped.
        this.addAbility(new CurseOfShallowTriggeredAbility());
    }

    public CurseOfShallowGraves(final CurseOfShallowGraves card) {
        super(card);
    }

    @Override
    public CurseOfShallowGraves copy() {
        return new CurseOfShallowGraves(this);
    }
}

class CurseOfShallowTriggeredAbility extends TriggeredAbilityImpl<CurseOfShallowTriggeredAbility> {

    public CurseOfShallowTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenTargetEffect(new ZombieToken(), new StaticValue(1), true, false), true);
    }

    public CurseOfShallowTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public CurseOfShallowTriggeredAbility(final CurseOfShallowTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(EventType.DECLARED_ATTACKERS)) {
            Permanent enchantment = game.getPermanent(this.getSourceId());
            if (enchantment != null
                    && enchantment.getAttachedTo() != null
                    && game.getCombat().getPlayerDefenders(game).contains(enchantment.getAttachedTo())) {
                for (Effect effect: this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(game.getCombat().getAttackerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player attacks enchanted player with one or more creatures, that attacking player may put a 2/2 black Zombie creature token onto the battlefield tapped.";
    }

    @Override
    public CurseOfShallowTriggeredAbility copy() {
        return new CurseOfShallowTriggeredAbility(this);
    }

}
