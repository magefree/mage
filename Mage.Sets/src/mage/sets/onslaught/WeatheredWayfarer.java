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
package mage.sets.onslaught;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class WeatheredWayfarer extends CardImpl<WeatheredWayfarer> {

    public WeatheredWayfarer(UUID ownerId) {
        super(ownerId, 59, "Weathered Wayfarer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "ONS";
        this.subtype.add("Human");
        this.subtype.add("Nomad");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}, {tap}: Search your library for a land card, reveal it, and put it into your hand. Then shuffle your library. Activate this ability only if an opponent controls more lands than you.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(new FilterLandCard()), true, true),
                new ManaCostsImpl("{W}"),
                new OpponentControllsMoreLandCondition()));
    }

    public WeatheredWayfarer(final WeatheredWayfarer card) {
        super(card);
    }

    @Override
    public WeatheredWayfarer copy() {
        return new WeatheredWayfarer(this);
    }
}

class OpponentControllsMoreLandCondition implements Condition {

    private static final FilterPermanent filter = new FilterLandPermanent();

    @Override
    public boolean apply(Game game, Ability source) {
        int numLands = game.getBattlefield().countAll(filter, source.getControllerId(), game);
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            if (numLands < game.getBattlefield().countAll(filter, opponentId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "an opponent controls more lands than you";
    }
}
