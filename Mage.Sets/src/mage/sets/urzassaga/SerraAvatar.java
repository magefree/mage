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
package mage.sets.urzassaga;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class SerraAvatar extends CardImpl<SerraAvatar> {

    public SerraAvatar(UUID ownerId) {
        super(ownerId, 45, "Serra Avatar", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");
        this.expansionSetCode = "USG";
        this.subtype.add("Avatar");

        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Serra Avatar's power and toughness are each equal to your life total.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.ALL, new SetPowerToughnessSourceEffect(new ControllerLifeCount(), Constants.Duration.EndOfGame)));
        // When Serra Avatar is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereTriggeredAbility(new SerraAvatarEffect()));
    }

    public SerraAvatar(final SerraAvatar card) {
        super(card);
    }

    @Override
    public SerraAvatar copy() {
        return new SerraAvatar(this);
    }
}

class SerraAvatarEffect extends OneShotEffect<SerraAvatarEffect> {
    SerraAvatarEffect() {
        super(Constants.Outcome.ReturnToHand);
        staticText = "shuffle it into its owner's library";
    }

    SerraAvatarEffect(final SerraAvatarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card c = player.getGraveyard().get(source.getSourceId(), game);
            if (c != null) {
                player.getGraveyard().remove(c);
                player.getLibrary().putOnTop(c, game);
                player.getLibrary().shuffle();
                return true;
            }

        }
        return false;
    }

    @Override
    public SerraAvatarEffect copy() {
        return new SerraAvatarEffect(this);
    }
}
