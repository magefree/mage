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
package mage.sets.shadowmoor;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class LureboundScarecrow extends CardImpl<LureboundScarecrow> {

    public LureboundScarecrow(UUID ownerId) {
        super(ownerId, 256, "Lurebound Scarecrow", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Scarecrow");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As Lurebound Scarecrow enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new LureboundScarecrowChooseColorEffect()));
        
        // When you control no permanents of the chosen color, sacrifice Lurebound Scarecrow.
        this.addAbility(new LureboundScarecrowTriggeredAbility());
    }

    public LureboundScarecrow(final LureboundScarecrow card) {
        super(card);
    }

    @Override
    public LureboundScarecrow copy() {
        return new LureboundScarecrow(this);
    }
}

class LureboundScarecrowChooseColorEffect extends OneShotEffect<LureboundScarecrowChooseColorEffect> {
    
    public LureboundScarecrowChooseColorEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "choose a color";
    }

    public LureboundScarecrowChooseColorEffect(final LureboundScarecrowChooseColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        StackObject sourceStackObject = game.getStack().getStackObject(source.getSourceId());
        Card card = game.getCard(source.getSourceId());
        if (player != null && sourceStackObject != null && card != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Constants.Outcome.BoostCreature, colorChoice, game)) {
                game.informPlayers(sourceStackObject.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                game.getState().setValue(card.getId() + "_color", colorChoice.getColor());
            }
        }
        return false;
    }

    @Override
    public LureboundScarecrowChooseColorEffect copy() {
        return new LureboundScarecrowChooseColorEffect(this);
    }
    
}

class LureboundScarecrowTriggeredAbility extends StateTriggeredAbility<LureboundScarecrowTriggeredAbility> {
    
    private static final String staticText = "When you control no permanents of the chosen color, sacrifice {this}.";

    public LureboundScarecrowTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public LureboundScarecrowTriggeredAbility(LureboundScarecrowTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE  || event.getType() == GameEvent.EventType.LOST_CONTROL
                || event.getType() == GameEvent.EventType.COLOR_CHANGED
                || event.getType() == GameEvent.EventType.SPELL_CAST) {
            Card card = game.getCard(this.getSourceId());
            if (card != null) {
                ObjectColor color = (ObjectColor) game.getState().getValue(card.getId() + "_color");
                if (color != null) {
                    for (Permanent perm: game.getBattlefield().getAllActivePermanents(controllerId)) {
                        if (perm.getColor().contains(color))
                            return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LureboundScarecrowTriggeredAbility copy() {
        return new LureboundScarecrowTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}
