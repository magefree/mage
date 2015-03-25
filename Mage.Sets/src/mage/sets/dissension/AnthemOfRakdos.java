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
package mage.sets.dissension;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author JotaPeRL
 */
public class AnthemOfRakdos extends CardImpl {

    public AnthemOfRakdos(UUID ownerId) {
        super(ownerId, 102, "Anthem of Rakdos", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{R}{R}");
        this.expansionSetCode = "DIS";

        // Whenever a creature you control attacks, it gets +2/+0 until end of turn and Anthem of Rakdos deals 1 damage to you.
        Effect effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setText("it gets +2/+0 until end of turn");
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(effect, false, true);
        effect = new DamageControllerEffect(1);
        effect.setText("and {this} deals 1 damage to you");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Hellbent - As long as you have no cards in hand, if a source you control would deal damage to a creature or player, it deals double that damage to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnthemOfRakdosHellbentEffect()));
    }

    public AnthemOfRakdos(final AnthemOfRakdos card) {
        super(card);
    }

    @Override
    public AnthemOfRakdos copy() {
        return new AnthemOfRakdos(this);
    }
}

class AnthemOfRakdosHellbentEffect extends ReplacementEffectImpl {

    public AnthemOfRakdosHellbentEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "<i>Hellbent</i> - As long as you have no cards in hand, if a source you control would deal damage to a creature or player, it deals double that damage to that creature or player instead.";
    }

    public AnthemOfRakdosHellbentEffect(final AnthemOfRakdosHellbentEffect effect) {
        super(effect);
    }

    @Override
    public AnthemOfRakdosHellbentEffect copy() {
        return new AnthemOfRakdosHellbentEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.DAMAGE_CREATURE)
                || event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getControllerId(event.getSourceId()).equals(source.getControllerId()) && HellbentCondition.getInstance().apply(game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }
}
