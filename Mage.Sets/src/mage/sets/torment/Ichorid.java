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
package mage.sets.torment;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Plopman
 */
public class Ichorid extends CardImpl<Ichorid> {

    public Ichorid(UUID ownerId) {
        super(ownerId, 65, "Ichorid", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "TOR";
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // At the beginning of the end step, sacrifice Ichorid.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new SacrificeSourceEffect(), false));
        // At the beginning of your upkeep, if Ichorid is in your graveyard, you may exile a black creature card other than Ichorid from your graveyard. If you do, return Ichorid to the battlefield.
        Ability ability = new IchoridTriggerdAbility();
        FilterCard filter = new FilterCreatureCard("an other black creature card from your graveyard");
        filter.add(Predicates.not(new CardIdPredicate(this.getId())));
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(filter)));
        this.addAbility(ability);
        
    }

    public Ichorid(final Ichorid card) {
        super(card);
    }

    @Override
    public Ichorid copy() {
        return new Ichorid(this);
    }
}

class IchoridTriggerdAbility extends BeginningOfUpkeepTriggeredAbility{
    
    public IchoridTriggerdAbility(){
        super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), TargetController.YOU, false);
    }

    public IchoridTriggerdAbility(IchoridTriggerdAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new IchoridTriggerdAbility(this);
    }
    

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player controller = game.getPlayer(controllerId);
        if(controller != null && controller.getGraveyard().contains(sourceId)){
            return super.checkInterveningIfClause(game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if {source} is in your graveyard, you may exile a black creature card other than {source} from your graveyard. If you do, return {source} to the battlefield";
    }
    
    
    
}