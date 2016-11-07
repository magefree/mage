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
package mage.cards.r;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class ReignOfThePit extends CardImpl {

    public ReignOfThePit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");

        // Each player sacrifices a creature. Create an X/X black Demon creature token with flying, where X is the total power of the creatures sacrificed this way.
        this.getSpellAbility().addEffect(new ReignOfThePitEffect());
    }

    public ReignOfThePit(final ReignOfThePit card) {
        super(card);
    }

    @Override
    public ReignOfThePit copy() {
        return new ReignOfThePit(this);
    }
}

class ReignOfThePitEffect extends OneShotEffect {
    
    ReignOfThePitEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each player sacrifices a creature. Create an X/X black Demon creature token with flying, where X is the total power of the creatures sacrificed this way";
    }
    
    ReignOfThePitEffect(final ReignOfThePitEffect effect) {
        super(effect);
    }
    
    @Override
    public ReignOfThePitEffect copy() {
        return new ReignOfThePitEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int totalPowerSacrificed = 0;
        List<UUID> perms = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent(), true);
                if (target.canChoose(player.getId(), game)) {
                    while (!target.isChosen() && player.canRespond()) {
                        player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                    }
                    perms.addAll(target.getTargets());
                }
            }
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                int power = permanent.getPower().getValue();
                if (permanent.sacrifice(source.getSourceId(), game)) {
                    totalPowerSacrificed += power;
                }
            }
        }
        new CreateTokenEffect(new ReignOfThePitToken(totalPowerSacrificed)).apply(game, source);
        return true;
    }
}

class ReignOfThePitToken extends Token {

    ReignOfThePitToken(int xValue) {
        super("Demon", "X/X black Demon creature token with flying");
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        color.setBlack(true);
        subtype.add("Demon");
        cardType.add(CardType.CREATURE);
        this.addAbility(FlyingAbility.getInstance());
    }
}
