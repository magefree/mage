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
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
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
 * @author LevelX2
 */
public class IonaShieldOfEmeria extends CardImpl {

    public IonaShieldOfEmeria(UUID ownerId) {
        super(ownerId, 13, "Iona, Shield of Emeria", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}{W}{W}{W}");
        this.expansionSetCode = "ZEN";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Iona, Shield of Emeria enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // Your opponents can't cast spells of the chosen color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IonaShieldOfEmeriaReplacementEffect()));

    }

    public IonaShieldOfEmeria(final IonaShieldOfEmeria card) {
        super(card);
    }

    @Override
    public IonaShieldOfEmeria copy() {
        return new IonaShieldOfEmeria(this);
    }
}

class IonaShieldOfEmeriaReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    IonaShieldOfEmeriaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast spells of the chosen color";
    }

    IonaShieldOfEmeriaReplacementEffect(final IonaShieldOfEmeriaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        ObjectColor chosenColor = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null && chosenColor != null) {
            return "You can't cast " + chosenColor.toString() +" spells (" + mageObject.getLogName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }    
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId()) ) {
            ObjectColor chosenColor = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            // spell is not on the stack yet, so we have to check the card
            Card card = game.getCard(event.getSourceId());
            if (chosenColor != null && card != null && card.getColor(game).contains(chosenColor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IonaShieldOfEmeriaReplacementEffect copy() {
        return new IonaShieldOfEmeriaReplacementEffect(this);
    }
}
