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
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author stravant
 */
public class TrespassersCurse extends CardImpl {

    public TrespassersCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever a creature enters the battlefield under enchanted player's control, that player loses 1 life and you gain 1 life.
        this.addAbility(new TrespassersCurseTriggeredAbility());
    }

    public TrespassersCurse(final TrespassersCurse card) {
        super(card);
    }

    @Override
    public TrespassersCurse copy() {
        return new TrespassersCurse(this);
    }
}

class TrespassersCurseTriggeredAbility extends TriggeredAbilityImpl {

    public TrespassersCurseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TrespassersCurseEffect(), false); // false because handled in effect
    }

    public TrespassersCurseTriggeredAbility(final TrespassersCurseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.sourceId);
        if (enchantment != null
                && enchantment.getAttachedTo() != null
                && game.getControllerId(event.getTargetId()).equals(enchantment.getAttachedTo())
                && game.getPermanent(event.getTargetId()).isCreature()) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever a creature enters the battlefield under enchanted player's control, ").append(super.getRule()).toString();
    }

    @Override
    public TrespassersCurseTriggeredAbility copy() {
        return new TrespassersCurseTriggeredAbility(this);
    }

}

class TrespassersCurseEffect extends OneShotEffect {

    public TrespassersCurseEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player loses 1 life and you gain 1 life.";
    }

    public TrespassersCurseEffect(final TrespassersCurseEffect effect) {
        super(effect);
    }

    @Override
    public TrespassersCurseEffect copy() {
        return new TrespassersCurseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controllerOfCreature = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controllerOfCreature != null) {
            controllerOfCreature.loseLife(1, game, false);
            controller.gainLife(1, game);
            return true;
        }
        return false;
    }
}
