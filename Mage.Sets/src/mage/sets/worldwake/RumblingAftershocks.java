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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class RumblingAftershocks extends CardImpl<RumblingAftershocks> {

    final static private FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    String rule = "As long as Quest for the Goblin Lord has five or more quest counters on it, creatures you control get +2/+0.";

    public RumblingAftershocks(UUID ownerId) {
        super(ownerId, 89, "Rumbling Aftershocks", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");
        this.expansionSetCode = "WWK";

        this.color.setRed(true);

        // Whenever you cast a kicked spell, you may have Rumbling Aftershocks deal damage to target creature or player equal to the number of times that spell was kicked.
        Ability ability = new RumblingAftershocksTriggeredAbility();
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

    }

    public RumblingAftershocks(final RumblingAftershocks card) {
        super(card);
    }

    @Override
    public RumblingAftershocks copy() {
        return new RumblingAftershocks(this);
    }
}

class RumblingAftershocksTriggeredAbility extends TriggeredAbilityImpl<RumblingAftershocksTriggeredAbility> {

    RumblingAftershocksTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RumblingAftershocksDealDamageEffect(), true);
    }

    RumblingAftershocksTriggeredAbility(final RumblingAftershocksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RumblingAftershocksTriggeredAbility copy() {
        return new RumblingAftershocksTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getControllerId().equals(controllerId)) {
                int damageAmount = 0;
                for (Ability ability: (Abilities<Ability>) spell.getAbilities()) {
                    if (ability instanceof KickerAbility) {
                        damageAmount += ((KickerAbility) ability).getKickedCounter();
                    }
                }
                if (damageAmount > 0) {
                    this.getEffects().get(0).setValue("damageAmount", new Integer(damageAmount));
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a kicked spell, " + super.getRule();
    }
}

class RumblingAftershocksDealDamageEffect extends OneShotEffect<RumblingAftershocksDealDamageEffect> {

    public RumblingAftershocksDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "you may have {this} deal damage to target creature or player equal to the number of times that spell was kicked";
    }

    public RumblingAftershocksDealDamageEffect(final RumblingAftershocksDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public RumblingAftershocksDealDamageEffect copy() {
        return new RumblingAftershocksDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Integer damageAmount = (Integer) this.getValue("damageAmount");
        if (player != null && damageAmount.intValue() > 0) {
            Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
               targetPlayer.damage(damageAmount.intValue(), source.getSourceId(), game, false, true);
               return true;
            }
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.damage(damageAmount.intValue(), source.getSourceId(), game, true, false);
                return true;
            }
        }
        return false;
    }
}
