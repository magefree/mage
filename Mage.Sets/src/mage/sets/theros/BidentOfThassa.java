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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class BidentOfThassa extends CardImpl<BidentOfThassa> {

    public BidentOfThassa(UUID ownerId) {
        super(ownerId, 42, "Bident of Thassa", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT, CardType.ARTIFACT}, "{2}{U}{U}");
        this.expansionSetCode = "THS";
        this.supertype.add("Legendary");

        this.color.setBlue(true);

        // Whenever a creature you control deals combat damage to a player, you may draw a card.
        this.addAbility(new BidentOfThassaTriggeredAbility());
        // {1}{U}, {T}: Creatures your opponents control attack this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BidentOfThassaMustAttackEffect(), new ManaCostsImpl("{1}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public BidentOfThassa(final BidentOfThassa card) {
        super(card);
    }

    @Override
    public BidentOfThassa copy() {
        return new BidentOfThassa(this);
    }
}

class BidentOfThassaTriggeredAbility extends TriggeredAbilityImpl<BidentOfThassaTriggeredAbility> {

    public BidentOfThassaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public BidentOfThassaTriggeredAbility(final BidentOfThassaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BidentOfThassaTriggeredAbility copy() {
        return new BidentOfThassaTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                Permanent creature = game.getPermanent(event.getSourceId());
                if (creature != null && creature.getControllerId().equals(controllerId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return " Whenever a creature you control deals combat damage to a player, you may draw a card.";
    }
}

class BidentOfThassaMustAttackEffect extends RequirementEffect<BidentOfThassaMustAttackEffect> {

    public BidentOfThassaMustAttackEffect() {
        super(Duration.EndOfTurn);
        staticText = "Creatures your opponents control attack this turn if able";
    }

    public BidentOfThassaMustAttackEffect(final BidentOfThassaMustAttackEffect effect) {
        super(effect);
    }

    @Override
    public BidentOfThassaMustAttackEffect copy() {
        return new BidentOfThassaMustAttackEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}
