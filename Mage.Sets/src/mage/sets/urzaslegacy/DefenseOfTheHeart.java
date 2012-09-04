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
package mage.sets.urzaslegacy;

import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public class DefenseOfTheHeart extends CardImpl<DefenseOfTheHeart> {

    public DefenseOfTheHeart(UUID ownerId) {
        super(ownerId, 100, "Defense of the Heart", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "ULG";

        this.color.setGreen(true);

        // At the beginning of your upkeep, if an opponent controls three or more creatures, sacrifice Defense of the Heart, search your library for up to two creature cards, and put those cards onto the battlefield. Then shuffle your library.
        TriggeredAbility ability  = new BeginningOfUpkeepTriggeredAbility(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect(), Constants.TargetController.YOU, false);
        ability.addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, new FilterCreatureCard()), false, Constants.Outcome.PutLandInPlay));
        DefenseOfTheHeartCondition contition = new DefenseOfTheHeartCondition();
        this.addAbility(new ConditionalTriggeredAbility(ability, contition, "At the beginning of your upkeep, if an opponent controls three or more creatures, sacrifice {this}, search your library for up to two creature cards, and put those cards onto the battlefield. Then shuffle your library"));
        
    }

    public DefenseOfTheHeart(final DefenseOfTheHeart card) {
        super(card);
    }

    @Override
    public DefenseOfTheHeart copy() {
        return new DefenseOfTheHeart(this);
    }
    
    class DefenseOfTheHeartCondition implements Condition {

        @Override
        public boolean apply(Game game, Ability source) {
            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            for(UUID uuid : opponents)
            {
                if(game.getBattlefield().countAll(new FilterCreaturePermanent(), uuid, game) >= 3)
                {
                    return true;
                }
            }
            return false;
        }
    }
}
