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
package mage.sets.ravnica;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class NetherbornPhalanx extends CardImpl {

    public NetherbornPhalanx(UUID ownerId) {
        super(ownerId, 99, "Netherborn Phalanx", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Horror");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Netherborn Phalanx enters the battlefield, each opponent loses 1 life for each creature he or she controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NetherbornPhalanxEffect());
        this.addAbility(ability);
        
        // Transmute {1}{B}{B}
        this.addAbility(new TransmuteAbility("{1}{B}{B}"));
    }

    public NetherbornPhalanx(final NetherbornPhalanx card) {
        super(card);
    }

    @Override
    public NetherbornPhalanx copy() {
        return new NetherbornPhalanx(this);
    }
}

class NetherbornPhalanxEffect extends OneShotEffect {

    NetherbornPhalanxEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each opponent loses 1 life for each creature he or she controls";
    }

    NetherbornPhalanxEffect(final NetherbornPhalanxEffect effect) {
        super(effect);
    }

    @Override
    public NetherbornPhalanxEffect copy() {
        return new NetherbornPhalanxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                final int count = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), playerId, game).size();
                if (count > 0) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        opponent.loseLife(count, game);
                    return true;
                    }
                }
            }
        }
        return false;
    }
}