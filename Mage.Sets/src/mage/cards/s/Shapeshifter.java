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
package mage.cards.s;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author MarcoMarin / HCrescent
 */
public class Shapeshifter extends CardImpl {

    public Shapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(7);

        // As Shapeshifter enters the battlefield, choose a number between 0 and 7.
        this.addAbility(new AsEntersBattlefieldAbility(new ShapeshifterEffect()));
        // At the beginning of your upkeep, you may choose a number between 0 and 7.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ShapeshifterEffect(), TargetController.YOU, true));        
        // Shapeshifter's power is equal to the last chosen number and its toughness is equal to 7 minus that number.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ShapeshifterContinuousEffect()));
    }

    public Shapeshifter(final Shapeshifter card) {
        super(card);
    }

    @Override
    public Shapeshifter copy() {
        return new Shapeshifter(this);
    }
}
class ShapeshifterEffect extends OneShotEffect {
    
    public ShapeshifterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose a number between 0 and 7.";
    }
    
    public ShapeshifterEffect(final ShapeshifterEffect effect) {
        super(effect);
    }
    
    @Override
    public ShapeshifterEffect copy() {
        return new ShapeshifterEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player Controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getPermanent(source.getSourceId());
        }
        if (Controller != null) {           
            Choice numberChoice = new ChoiceImpl();
            numberChoice.setMessage("Choose a number beween 0 and 7");
            HashSet<String> numbers = new HashSet<>();
            for (int i = 0; i <= 7; i++) {
                numbers.add(Integer.toString(i));
            }
            numberChoice.setChoices(numbers);
            while (!Controller.choose(Outcome.Neutral, numberChoice, game)) {
                if (!Controller.canRespond()) {
                    return false;
                }
            }
            game.informPlayers("Shapeshifter, chosen number: [" + numberChoice.getChoice() + ']');
            game.getState().setValue(source.getSourceId().toString() + "_Shapeshifter", numberChoice.getChoice());
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("lastChosenNumber", CardUtil.addToolTipMarkTags("Last chosen number: " + numberChoice.getChoice()), game);
                }
        }
        return false;
    }
}
class ShapeshifterContinuousEffect extends ContinuousEffectImpl {
    
    public ShapeshifterContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the last chosen number and its toughness is equal to 7 minus that number.";
    }
    
    public ShapeshifterContinuousEffect(final ShapeshifterContinuousEffect effect) {
        super(effect);
    }
    
    @Override
    public ShapeshifterContinuousEffect copy() {
        return new ShapeshifterContinuousEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
            if(permanent!=null){
                String lastChosen = (String) game.getState().getValue(source.getSourceId().toString() + "_Shapeshifter");
                int lastChosenNumber = Integer.parseInt(lastChosen);
                permanent.getPower().modifyBaseValue(lastChosenNumber);
                permanent.getToughness().modifyBaseValue(7 - lastChosenNumber);
                return true;
            }
        return false;
    }
}