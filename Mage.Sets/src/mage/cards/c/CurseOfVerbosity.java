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
package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EnchantedPlayerAttackedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Saga
 */
public class CurseOfVerbosity extends CardImpl {

    public CurseOfVerbosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted player is attacked, draw a card. Each opponent attacking that player does the same.
        this.addAbility(new EnchantedPlayerAttackedTriggeredAbility(new CurseOfVerbosityEffect()));
    }

    public CurseOfVerbosity(final CurseOfVerbosity card) {
        super(card);
    }

    @Override
    public CurseOfVerbosity copy() {
        return new CurseOfVerbosity(this);
    }
}

class CurseOfVerbosityEffect extends OneShotEffect {

    CurseOfVerbosityEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw a card. Each opponent attacking that player does the same.";
    }

    CurseOfVerbosityEffect(final CurseOfVerbosityEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfVerbosityEffect copy() {
        return new CurseOfVerbosityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (enchantment != null) {
            Player enchantedPlayer = game.getPlayer(enchantment.getAttachedTo());
            if (enchantedPlayer != null) {
                Set<UUID> players = new HashSet();
                for (UUID attacker : game.getCombat().getAttackers()) {
                    UUID defender = game.getCombat().getDefenderId(attacker);
                    if (defender.equals(enchantedPlayer.getId())
                            && game.getPlayer(source.getControllerId()).hasOpponent(game.getPermanent(attacker).getControllerId(), game)) {
                        players.add(game.getPermanent(attacker).getControllerId());
                    }
                }
                players.add(source.getControllerId());
                for (UUID player : players) {
                    game.getPlayer(player);
                    Effect effect = new DrawCardTargetEffect(1);
                    effect.setTargetPointer(new FixedTarget(player));
                    effect.apply(game, source);
                }
            }
            return true;

        }
        return false;
    }
}
