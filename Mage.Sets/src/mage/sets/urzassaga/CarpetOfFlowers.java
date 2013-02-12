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

import java.util.LinkedHashSet;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.choices.ChoiceImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public class CarpetOfFlowers extends CardImpl<CarpetOfFlowers> {

    public CarpetOfFlowers(UUID ownerId) {
        super(ownerId, 240, "Carpet of Flowers", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "USG";

        this.color.setGreen(true);

        // At the beginning of each of your main phases, if you haven't added mana to your mana pool with this ability this turn, you may add up to X mana of any one color to your mana pool, where X is the number of Islands target opponent controls.
        this.addAbility(new CarpetOfFlowersTriggeredAbility());
    }

    public CarpetOfFlowers(final CarpetOfFlowers card) {
        super(card);
    }

    @Override
    public CarpetOfFlowers copy() {
        return new CarpetOfFlowers(this);
    }
}



class CarpetOfFlowersTriggeredAbility extends TriggeredAbilityImpl<CarpetOfFlowersTriggeredAbility> {


    public CarpetOfFlowersTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new CarpetOfFlowersEffect(), true);
        this.addChoice(new ChoiceColor());
        this.addTarget(new TargetOpponent());
        
    }

    public CarpetOfFlowersTriggeredAbility(final CarpetOfFlowersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CarpetOfFlowersTriggeredAbility copy() {
        return new CarpetOfFlowersTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if((event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE || event.getType() == GameEvent.EventType.POSTCOMBAT_MAIN_PHASE_PRE) && event.getPlayerId().equals(this.controllerId)){
            return true;
        }  
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Boolean activated = (Boolean)game.getState().getValue(this.originalId.toString() + "addMana");
        if (activated == null)
        {
            return true;
        }
        else
        {
            return !activated;
        }
    }
    

    
    @Override
    public boolean resolve(Game game) {
        boolean value = super.resolve(game);
        if(value == true)
        {
            game.getState().setValue(this.originalId.toString() + "addMana", Boolean.TRUE);
        }
        return value;
    }
    
    @Override
    public void reset(Game game) {
        game.getState().setValue(this.originalId.toString() + "addMana", Boolean.FALSE);
    }
        
    @Override
    public String getRule() {
        return "At the beginning of each of your main phases, if you haven't added mana to your mana pool with this ability this turn";
    }

}


class CarpetOfFlowersEffect extends ManaEffect<CarpetOfFlowersEffect> {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Island ");

    static {
        filter.add(new SubtypePredicate("Island"));
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    CarpetOfFlowersEffect() {
        super();
        staticText = "add up to X mana of any one color to your mana pool, where X is the number of Islands target opponent controls";
    }

    CarpetOfFlowersEffect(final CarpetOfFlowersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
        Player player = game.getPlayer(source.getControllerId());
        if(player != null){
            int countMax = game.getBattlefield().count(filter, source.getSourceId(), source.getTargets().getFirstTarget(), game);
            ChoiceImpl choiceCount = new ChoiceImpl(true);
            LinkedHashSet<String> set = new LinkedHashSet<String>();
            for(int i = 0; i <= countMax; i++)
            {
                set.add(Integer.toString(i));
            }
            choiceCount.setChoices(set);
            choiceCount.setMessage("Choose number of mana");
            player.choose(Outcome.PutManaInPool, choiceCount, game);
            int count = Integer.parseInt(choiceCount.getChoice());
            if (choice.getColor().isBlack()) {
                player.getManaPool().addMana(new Mana(0, 0, 0, 0, count, 0, 0), game, source);
                return true;
            } else if (choice.getColor().isBlue()) {
                player.getManaPool().addMana(new Mana(0, 0, count, 0, 0, 0, 0), game, source);
                return true;
            } else if (choice.getColor().isRed()) {
                player.getManaPool().addMana(new Mana(count, 0, 0, 0, 0, 0, 0), game, source);
                return true;
            } else if (choice.getColor().isGreen()) {
                player.getManaPool().addMana(new Mana(0, count, 0, 0, 0, 0, 0), game, source);
                return true;
            } else if (choice.getColor().isWhite()) {
                player.getManaPool().addMana(new Mana(0, 0, 0, count, 0, 0, 0), game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public CarpetOfFlowersEffect copy() {
        return new CarpetOfFlowersEffect(this);
    }
}
