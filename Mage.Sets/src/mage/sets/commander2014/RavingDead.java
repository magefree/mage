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
package mage.sets.commander2014;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 *
 * @author LevelX2
 */
public class RavingDead extends CardImpl {

    public RavingDead(UUID ownerId) {
        super(ownerId, 29, "Raving Dead", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "C14";
        this.subtype.add("Zombie");

        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // At the beginning of combat on your turn, choose an opponent at random. Raving Dead attacks that player this combat if able.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new RavingDeadEffect(), TargetController.YOU, false));
        // Whenever Raving Dead deals combat damage to a player, that player loses half his or her life, rounded down.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RavingDeadDamageEffect(), false, true));
    }

    public RavingDead(final RavingDead card) {
        super(card);
    }

    @Override
    public RavingDead copy() {
        return new RavingDead(this);
    }
}

class RavingDeadEffect extends OneShotEffect {

    public RavingDeadEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose an opponent at random. {this} attacks that player this combat if able";
    }

    public RavingDeadEffect(final RavingDeadEffect effect) {
        super(effect);
    }

    @Override
    public RavingDeadEffect copy() {
        return new RavingDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<UUID> opponents = new ArrayList<>();
            opponents.addAll(game.getOpponents(controller.getId()));
            Player opponent = game.getPlayer(opponents.get(RandomUtil.nextInt(opponents.size())));
            if (opponent != null) {
                ContinuousEffect effect = new AttacksIfAbleTargetPlayerSourceEffect();
                effect.setTargetPointer(new FixedTarget(opponent.getId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}

class RavingDeadDamageEffect extends OneShotEffect {

    public RavingDeadDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "that player loses half his or her life, rounded up";
    }

    public RavingDeadDamageEffect(final RavingDeadDamageEffect effect) {
        super(effect);
    }

    @Override
    public RavingDeadDamageEffect copy() {
        return new RavingDeadDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            Integer amount = (int) Math.ceil(player.getLife() / 2f);
            if (amount > 0) {
                player.loseLife(amount, game);
            }
            return true;
        }
        return false;
    }
}

class AttacksIfAbleTargetPlayerSourceEffect extends RequirementEffect {

    public AttacksIfAbleTargetPlayerSourceEffect() {
        super(Duration.EndOfTurn);
        staticText = "{this} attacks that player this combat if able";
    }

    public AttacksIfAbleTargetPlayerSourceEffect(final AttacksIfAbleTargetPlayerSourceEffect effect) {
        super(effect);
    }

    @Override
    public AttacksIfAbleTargetPlayerSourceEffect copy() {
        return new AttacksIfAbleTargetPlayerSourceEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
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

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return getTargetPointer().getFirst(game, source);
    }

}
