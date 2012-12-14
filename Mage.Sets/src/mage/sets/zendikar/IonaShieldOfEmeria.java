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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class IonaShieldOfEmeria extends CardImpl<IonaShieldOfEmeria> {

    public IonaShieldOfEmeria(UUID ownerId) {
        super(ownerId, 13, "Iona, Shield of Emeria", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}{W}{W}{W}");
        this.expansionSetCode = "ZEN";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");
        this.color.setWhite(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Iona, Shield of Emeria enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new IonaShieldOfEmeriaChooseColorEffect()));

        // Your opponents can't cast spells of the chosen color.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new IonaShieldOfEmeriaReplacementEffect()));

    }

    public IonaShieldOfEmeria(final IonaShieldOfEmeria card) {
        super(card);
    }

    @Override
    public IonaShieldOfEmeria copy() {
        return new IonaShieldOfEmeria(this);
    }
}

class IonaShieldOfEmeriaChooseColorEffect extends OneShotEffect<IonaShieldOfEmeriaChooseColorEffect> {

    public IonaShieldOfEmeriaChooseColorEffect() {
        super(Constants.Outcome.Detriment);
        staticText = "choose a color";
    }

    public IonaShieldOfEmeriaChooseColorEffect(final IonaShieldOfEmeriaChooseColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent perm = game.getPermanent(source.getSourceId());
        if (player != null && perm != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Constants.Outcome.Detriment, colorChoice, game)) {
                game.informPlayers(perm.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                game.getState().setValue(perm.getId() + "_color", colorChoice.getColor());
            }
        }
        return false;
    }

    @Override
    public IonaShieldOfEmeriaChooseColorEffect copy() {
        return new IonaShieldOfEmeriaChooseColorEffect(this);
    }
}

class IonaShieldOfEmeriaReplacementEffect extends ReplacementEffectImpl<IonaShieldOfEmeriaReplacementEffect> {
    IonaShieldOfEmeriaReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Your opponents can't cast spells of the chosen color";
    }

    IonaShieldOfEmeriaReplacementEffect(final IonaShieldOfEmeriaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId()) ) {
                ObjectColor chosenColor = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
                Card card = game.getCard(event.getSourceId());
                if (chosenColor != null && card != null && card.getColor().contains(chosenColor)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public IonaShieldOfEmeriaReplacementEffect copy() {
        return new IonaShieldOfEmeriaReplacementEffect(this);
    }
}