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
package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jerekwilson
 */
public class RhysticCave extends CardImpl {

    public RhysticCave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Choose a color.  
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                          new ChooseColorEffect(Outcome.PutManaInPool),
                          new TapSourceCost());
        
        // Add one mana of that color to your mana pool unless any player pays {1}. Activate this ability only any time you could cast an instant.
        ability.addEffect(new DoUnlessAnyPlayerPaysEffect(new RhysticCaveManaEffect(),new GenericManaCost(1)));
        
        this.addAbility(ability);
        
    }

    public RhysticCave(final RhysticCave card) {
        super(card);
    }

    @Override
    public RhysticCave copy() {
        return new RhysticCave(this);
    }
    
    
    class RhysticCaveManaEffect extends ManaEffect {
        
        private final Mana chosenMana;
        
        public RhysticCaveManaEffect() {
            super();
            chosenMana = new Mana();
            this.staticText = "Add one mana of that color to your mana pool ";
        }

        public RhysticCaveManaEffect(final RhysticCaveManaEffect effect) {
            super(effect);
            this.chosenMana = effect.chosenMana.copy();
        }

        @Override
        public Mana getMana(Game game, Ability source) {
            return null;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId()); 
            MageObject mageObject = game.getPermanentOrLKIBattlefield(source.getSourceId()); //get obj reference to Rhystic Cave        
            if (controller != null) {    
                if (mageObject != null) {
                    ObjectColor choice = (ObjectColor) game.getState().getValue(mageObject.getId()+"_color");
                    if (choice!= null) {
                        String color = choice.toString();
                        switch (color) {
                            case "R":
                                chosenMana.setRed(1);
                                break;
                            case "U":
                                chosenMana.setBlue(1);
                                break;
                            case "W":
                                chosenMana.setWhite(1);
                                break;
                            case "B":
                                chosenMana.setBlack(1);
                                break;
                            case "G":
                                chosenMana.setGreen(1);
                                break;
                        }
                    }
                    checkToFirePossibleEvents(chosenMana, game, source);
                    controller.getManaPool().addMana(chosenMana, game, source);
                    return true;
                }
                
            }
            
            return false;          
        }

        @Override
        public Effect copy() {
            return new RhysticCaveManaEffect(this);
        }
    }
    
}
