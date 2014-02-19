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
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PaintersServant extends CardImpl<PaintersServant> {

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

class ChooseColorEffect extends OneShotEffect<ChooseColorEffect> {

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
        Card card = game.getCard(source.getSourceId());
        if (player != null && card != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Outcome.Neutral, colorChoice, game)) {
                game.informPlayers(new StringBuilder(card.getName()).append(": ").append(player.getName()).append(" has chosen ").append(colorChoice.getChoice()).toString());
                game.getState().setValue(source.getSourceId() + "_color", colorChoice.getColor());
            }
        }
        return false;
    }

    @Override
    public ChooseColorEffect copy() {
        return new ChooseColorEffect(this);
    }

}

class PaintersServantEffect extends ContinuousEffectImpl<PaintersServantEffect> {

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
                switch (colorString) {
                    case "W":
                        perm.getColor().setWhite(true);
                        break;
                    case "B":
                        perm.getColor().setBlack(true);
                        break;
                    case "U":
                        perm.getColor().setBlue(true);
                        break;
                    case "G":
                        perm.getColor().setGreen(true);
                        break;
                    case "R":
                        perm.getColor().setRed(true);
                        break;
                }
            }
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    // Hand
                    for (Card card : player.getHand().getCards(game)) {
                        switch (colorString) {
                            case "W":
                                card.getColor().setWhite(true);
                                break;
                            case "B":
                                card.getColor().setBlack(true);
                                break;
                            case "U":
                                card.getColor().setBlue(true);
                                break;
                            case "G":
                                card.getColor().setGreen(true);
                                break;
                            case "R":
                                card.getColor().setRed(true);
                                break;
                        }
                    }
                    // Library
                    for (Card card : player.getLibrary().getCards(game)) {
                        switch (colorString) {
                            case "W":
                                card.getColor().setWhite(true);
                                break;
                            case "B":
                                card.getColor().setBlack(true);
                                break;
                            case "U":
                                card.getColor().setBlue(true);
                                break;
                            case "G":
                                card.getColor().setGreen(true);
                                break;
                            case "R":
                                card.getColor().setRed(true);
                                break;
                        }
                    }

                }

                // Stack
                for (MageObject object : game.getStack()) {
                    if (object instanceof Spell) {
                        switch (colorString) {
                            case "W":
                                object.getColor().setWhite(true);
                                break;
                            case "B":
                                object.getColor().setBlack(true);
                                break;
                            case "U":
                                object.getColor().setBlue(true);
                                break;
                            case "G":
                                object.getColor().setGreen(true);
                                break;
                            case "R":
                                object.getColor().setRed(true);
                                break;
                        }
                    }
                }
                // Exile
                for (Card card : game.getExile().getAllCards(game)) {
                    switch (colorString) {
                        case "W":
                            card.getColor().setWhite(true);
                            break;
                        case "B":
                            card.getColor().setBlack(true);
                            break;
                        case "U":
                            card.getColor().setBlue(true);
                            break;
                        case "G":
                            card.getColor().setGreen(true);
                            break;
                        case "R":
                            card.getColor().setRed(true);
                            break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PaintersServantEffect copy() {
        return new PaintersServantEffect(this);
    }

    private PaintersServantEffect(PaintersServantEffect effect) {
        super(effect);
    }

}
