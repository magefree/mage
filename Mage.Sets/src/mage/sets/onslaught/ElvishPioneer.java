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
package mage.sets.onslaught;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Plopman
 */
public class ElvishPioneer extends CardImpl<ElvishPioneer> {

    public ElvishPioneer(UUID ownerId) {
        super(ownerId, 257, "Elvish Pioneer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "ONS";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Elvish Pioneer enters the battlefield, you may put a basic land card from your hand onto the battlefield tapped.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutLandOnBattlefieldEffect(), false));
    }

    public ElvishPioneer(final ElvishPioneer card) {
        super(card);
    }

    @Override
    public ElvishPioneer copy() {
        return new ElvishPioneer(this);
    }
}

class PutLandOnBattlefieldEffect extends OneShotEffect<PutLandOnBattlefieldEffect> {

    private static final String choiceText = "Put a land card from your hand onto the battlefield?";

    public PutLandOnBattlefieldEffect() {
        super(Constants.Outcome.PutLandInPlay);
        this.staticText = "put a land card from your hand onto the battlefield";
    }

    public PutLandOnBattlefieldEffect(final PutLandOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public PutLandOnBattlefieldEffect copy() {
        return new PutLandOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Constants.Outcome.PutLandInPlay, choiceText, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(new FilterLandCard());
        if (player.choose(Constants.Outcome.PutLandInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                card.putOntoBattlefield(game, Constants.Zone.HAND, source.getId(), source.getControllerId());
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                        permanent.setTapped(true);
                }
                return true;
            }
        }
        return false;
    }
}