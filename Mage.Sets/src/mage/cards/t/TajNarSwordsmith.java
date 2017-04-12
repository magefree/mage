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
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class TajNarSwordsmith extends CardImpl {

    public TajNarSwordsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add("Cat");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Taj-Nar Swordsmith enters the battlefield, you may pay {X}. If you do, search your library for an Equipment card with converted mana cost X or less and put that card onto the battlefield. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TajNarSwordsmithEffect()));
    }

    public TajNarSwordsmith(final TajNarSwordsmith card) {
        super(card);
    }

    @Override
    public TajNarSwordsmith copy() {
        return new TajNarSwordsmith(this);
    }
}

class TajNarSwordsmithEffect extends OneShotEffect {
    
    TajNarSwordsmithEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {X}. If you do, search your library for an Equipment card with converted mana cost X or less and put that card onto the battlefield. Then shuffle your library";
    }
    
    TajNarSwordsmithEffect(final TajNarSwordsmithEffect effect) {
        super(effect);
    }
    
    @Override
    public TajNarSwordsmithEffect copy() {
        return new TajNarSwordsmithEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.chooseUse(Outcome.BoostCreature, "Do you want to to pay {X}?", source, game)) {
            int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            Cost cost = new GenericManaCost(costX);
            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                FilterCard filter = new FilterCard("Equipment card with converted mana cost " + costX + " or less");
                filter.add(new SubtypePredicate("Equipment"));
                filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, costX + 1));
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, filter), false, true).apply(game, source);
                return true;
            }
        }
        return false;
    }
}
