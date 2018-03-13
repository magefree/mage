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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public class CharmedGriffin extends CardImpl {

    public CharmedGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Charmed Griffin enters the battlefield, each other player may put an artifact or enchantment card onto the battlefield from his or her hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CharmedGriffinEffect(), false));
    }

    public CharmedGriffin(final CharmedGriffin card) {
        super(card);
    }

    @Override
    public CharmedGriffin copy() {
        return new CharmedGriffin(this);
    }
}

class CharmedGriffinEffect extends OneShotEffect {

    public CharmedGriffinEffect() {
        super(Outcome.Detriment);
        this.staticText = "each other player may put an artifact or enchantment card onto the battlefield from his or her hand";
    }

    public CharmedGriffinEffect(final CharmedGriffinEffect effect) {
        super(effect);
    }

    @Override
    public CharmedGriffinEffect copy() {
        return new CharmedGriffinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        TargetCardInHand target = new TargetCardInHand(new FilterArtifactOrEnchantmentCard());
                        if (target.canChoose(source.getSourceId(), playerId, game)
                                && player.chooseUse(Outcome.Neutral, "Put an artifact or enchantment card from your hand onto the battlefield?", source, game)
                                && player.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                card.putOntoBattlefield(game, Zone.HAND, source.getSourceId(), player.getId());
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
