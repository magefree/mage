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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.game.permanent.token.EmptyToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class TemptWithReflections extends CardImpl<TemptWithReflections> {

    public TemptWithReflections(UUID ownerId) {
        super(ownerId, 60, "Tempt with Reflections", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "C13";

        this.color.setBlue(true);

        // Tempting offer - Choose target creature you control. Put a token onto the battlefield that's a copy of that creature. Each opponent may put a token onto the battlefield that's a copy of that creature. For each opponent who does, put a token onto the battlefield that's a copy of that creature.
        this.getSpellAbility().addEffect(new TemptWithReflectionsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(true));
    }

    public TemptWithReflections(final TemptWithReflections card) {
        super(card);
    }

    @Override
    public TemptWithReflections copy() {
        return new TemptWithReflections(this);
    }
}

class TemptWithReflectionsEffect extends OneShotEffect<TemptWithReflectionsEffect> {

    public TemptWithReflectionsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "<i>Tempting offer</i> - Choose target creature you control. Put a token onto the battlefield that's a copy of that creature. Each opponent may put a token onto the battlefield that's a copy of that creature. For each opponent who does, put a token onto the battlefield that's a copy of that creature";
    }

    public TemptWithReflectionsEffect(final TemptWithReflectionsEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithReflectionsEffect copy() {
        return new TemptWithReflectionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        if (permanent != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(permanent);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());

            Set<UUID> playersSaidYes = new HashSet<UUID>();
            PlayerList playerList = game.getPlayerList().copy();
            playerList.setCurrent(game.getActivePlayerId());
            Player player = game.getPlayer(game.getActivePlayerId());
            do {
                if (game.getOpponents(source.getControllerId()).contains(player.getId())) {
                    String decision;
                    if (player.chooseUse(outcome, "Put a copy of target creature onto the battlefield for you?", game)) {
                        playersSaidYes.add(player.getId());
                        decision = " chooses to copy ";
                    } else {
                        decision = " won't copy ";
                    }
                    game.informPlayers((new StringBuilder(player.getName()).append(decision).append(permanent.getName()).toString()));
                }
                player = playerList.getNext(game);
            } while (!player.getId().equals(game.getActivePlayerId()));

            for (UUID playerId: playersSaidYes) {
                token = new EmptyToken();
                CardUtil.copyTo(token).from(permanent);
                token.putOntoBattlefield(1, game, source.getSourceId(), playerId);
            }

            if (playersSaidYes.size() > 0) {
                token = new EmptyToken();
                CardUtil.copyTo(token).from(permanent);
                token.putOntoBattlefield(playersSaidYes.size(), game, source.getSourceId(), source.getControllerId());
            }
            return true;
        }
        return false;
    }
}
