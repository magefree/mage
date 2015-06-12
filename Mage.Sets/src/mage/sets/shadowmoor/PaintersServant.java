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

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.sets.Commander;

/**
 *
 * @author LevelX2
 */
public class PaintersServant extends CardImpl {

    public PaintersServant(UUID ownerId) {
        super(ownerId, 257, "Painter's Servant", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Scarecrow");

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As Painter's Servant enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect()));

        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PaintersServantEffect()));
    }

    public PaintersServant(final PaintersServant card) {
        super(card);
    }

    @Override
    public PaintersServant copy() {
        return new PaintersServant(this);
    }
}

class ChooseColorEffect extends OneShotEffect {

    public ChooseColorEffect() {
        super(Outcome.Detriment);
        staticText = "choose a color";
    }

    public ChooseColorEffect(final ChooseColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Outcome.Neutral, colorChoice, game)) {
                game.informPlayers(new StringBuilder(permanent.getName()).append(": ").append(player.getLogName()).append(" has chosen ").append(colorChoice.getChoice()).toString());
                game.getState().setValue(source.getSourceId() + "_color", colorChoice.getColor());
                permanent.addInfo("chosen color", "<font color = 'blue'>Chosen color: " + colorChoice.getColor().getDescription() + "</font>", game);
            }
            return true;
        }
        return false;
    }

    @Override
    public ChooseColorEffect copy() {
        return new ChooseColorEffect(this);
    }

}

class PaintersServantEffect extends ContinuousEffectImpl {

    public PaintersServantEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            if (color == null) {
                return false;
            }
            String colorString = color.toString();
            for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                setObjectColor(perm, colorString, game);
            }
            // Stack
            for (MageObject object : game.getStack()) {
                if (object instanceof Spell) {
                    setObjectColor(object, colorString, game);
                }
            }
            // Exile
            for (Card card : game.getExile().getAllCards(game)) {
                setCardColor(card, colorString, game);
            }
            // Command
            for (CommandObject commandObject : game.getState().getCommand()) {
                if (commandObject instanceof Commander) {
                    setObjectColor(commandObject, colorString, game);
                }
            }

            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    // Hand
                    for (Card card : player.getHand().getCards(game)) {
                        setCardColor(card, colorString, game);
                    }
                    // Library
                    for (Card card : player.getLibrary().getCards(game)) {
                        setCardColor(card, colorString, game);
                    }
                    // Graveyard
                    for (Card card : player.getGraveyard().getCards(game)) {
                        setCardColor(card, colorString, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    protected static void setCardColor(Card card, String colorString, Game game) {
        ObjectColor color = game.getState().getCreateCardAttribute(card).getColor();
        switch (colorString) {            
            case "W":
                color.setWhite(true);
                break;
            case "B":
                color.setBlack(true);
                break;
            case "U":
                color.setBlue(true);
                break;
            case "G":
                color.setGreen(true);
                break;
            case "R":
                color.setRed(true);
                break;
        }
    }

    protected static void setObjectColor(MageObject obj, String colorString, Game game) {
        switch (colorString) {
            case "W":
                obj.getColor(game).setWhite(true);
                break;
            case "B":
                obj.getColor(game).setBlack(true);
                break;
            case "U":
                obj.getColor(game).setBlue(true);
                break;
            case "G":
                obj.getColor(game).setGreen(true);
                break;
            case "R":
                obj.getColor(game).setRed(true);
                break;
        }
    }

    @Override
    public PaintersServantEffect copy() {
        return new PaintersServantEffect(this);
    }

    private PaintersServantEffect(PaintersServantEffect effect) {
        super(effect);
    }

}
