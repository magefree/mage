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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Eirkei
 */
public class ArchangelOfStrife extends CardImpl {

    public ArchangelOfStrife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add("Angel");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // As Archangel of Strife enters the battlefield, each player chooses war or peace.
        this.addAbility(new EntersBattlefieldAbility(new ArchangelOfStrifeChooseEffect(), "As Archangel of Strife enters the battlefield, each player chooses war or peace."));
        
        // Creatures controlled by players who chose war get +3/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArchangelOfStrifeWarEffect()));
        
        // Creatures controlled by players who chose peace get +0/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArchangelOfStrifePeaceEffect()));
    }

    public ArchangelOfStrife(final ArchangelOfStrife card) {
        super(card);
    }

    @Override
    public ArchangelOfStrife copy() {
        return new ArchangelOfStrife(this);
    }
}

class ArchangelOfStrifeChooseEffect extends OneShotEffect {

    public ArchangelOfStrifeChooseEffect(){
        super(Outcome.Neutral);
        
        this.staticText = "each player chooses war or peace.";
    }
    
    public ArchangelOfStrifeChooseEffect(ArchangelOfStrifeChooseEffect effect){
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        
        if (sourcePermanent == null) {
            sourcePermanent = game.getPermanentEntering(source.getSourceId());
        }
        
        if (controller != null && sourcePermanent != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                
                Choice choice = new ChoiceImpl(true);
                choice.setMessage("Select war or peace");
                choice.getChoices().add("war");
                choice.getChoices().add("peace");
                
                while (!choice.isChosen()) {
                    if (!player.canRespond()) {
                        return false;
                    }
                    
                    player.choose(Outcome.Neutral, choice, game);
                }
                
                if (choice.isChosen()) {
                    if (!game.isSimulation()) {
                        game.informPlayers(sourcePermanent.getLogName() + ": " + player.getLogName() + " has chosen " + choice.getChoice());
                    }
                    
                    game.getState().setValue(playerId + "_" + source.getSourceId() + "_modeChoice", choice.getChoice());
                    sourcePermanent.addInfo("_" + playerId +"_modeChoice", "<font color = 'blue'>" + player.getName() + " chose: " + choice.getChoice() + "</font>", game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new ArchangelOfStrifeChooseEffect(this);
    }
    
}

class ArchangelOfStrifeWarEffect extends BoostAllEffect{
    private static final FilterCreaturePermanent creaturefilter = new FilterCreaturePermanent("Creatures controlled by players who chose war");
    
    public ArchangelOfStrifeWarEffect(){
        super(3, 0, Duration.WhileOnBattlefield, creaturefilter, false);
    }
    
    @Override
    protected boolean selectedByRuntimeData(Permanent permanent, Ability source, Game game) {
        if (permanent != null){
            UUID controllerId = permanent.getControllerId();

            String choosenMode = (String) game.getState().getValue(controllerId + "_" + source.getSourceId() + "_modeChoice");

            return  creaturefilter.match(permanent, game) && choosenMode != null && choosenMode.equals("war");
        }
        
        return false;
    }
    
    public ArchangelOfStrifeWarEffect(ArchangelOfStrifeWarEffect effect) {
        super(effect);
    }

    @Override
    public ArchangelOfStrifeWarEffect copy() {
        return new ArchangelOfStrifeWarEffect(this);
    }
}

class ArchangelOfStrifePeaceEffect extends BoostAllEffect{
    private static final FilterCreaturePermanent creaturefilter = new FilterCreaturePermanent("Creatures controlled by players who chose peace");
    
    public ArchangelOfStrifePeaceEffect(){
        super(0, 3, Duration.WhileOnBattlefield, creaturefilter, false);
    }
    
    @Override
    protected boolean selectedByRuntimeData(Permanent permanent, Ability source, Game game) {
        if (permanent != null){
            UUID controllerId = permanent.getControllerId();

            String choosenMode = (String) game.getState().getValue(controllerId + "_" + source.getSourceId() + "_modeChoice");

            return  creaturefilter.match(permanent, game) && choosenMode != null && choosenMode.equals("peace");
        }
        
        return false;
    }
    
    public ArchangelOfStrifePeaceEffect(ArchangelOfStrifePeaceEffect effect) {
        super(effect);
    }

    @Override
    public ArchangelOfStrifePeaceEffect copy() {
        return new ArchangelOfStrifePeaceEffect(this);
    }
}