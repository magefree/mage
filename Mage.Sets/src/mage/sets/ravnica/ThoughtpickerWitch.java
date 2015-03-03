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
package mage.sets.ravnica;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public class ThoughtpickerWitch extends CardImpl {

    public ThoughtpickerWitch(UUID ownerId) {
        super(ownerId, 109, "Thoughtpicker Witch", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, Sacrifice a creature: Look at the top two cards of target opponent's library, then exile one of them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThoughtpickerWitchEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("a creature"))));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public ThoughtpickerWitch(final ThoughtpickerWitch card) {
        super(card);
    }

    @Override
    public ThoughtpickerWitch copy() {
        return new ThoughtpickerWitch(this);
    }
}

class ThoughtpickerWitchEffect extends OneShotEffect {
    
    ThoughtpickerWitchEffect() {
        super(Outcome.Exile);
        this.staticText = "Look at the top two cards of target opponent's library, then exile one of them";
    }
    
    ThoughtpickerWitchEffect(final ThoughtpickerWitchEffect effect) {
        super(effect);
    }
    
    @Override
    public ThoughtpickerWitchEffect copy() {
        return new ThoughtpickerWitchEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && opponent != null) {
            Cards cards = new CardsImpl();
            int numLooked = Math.min(2, opponent.getLibrary().size());
            if (numLooked > 0) {
                for (int i = 0; i < numLooked; i++) {
                    cards.add(opponent.getLibrary().removeFromTop(game));
                }
                TargetCard target = new TargetCardInLibrary(new FilterCard("card to exile"));
                controller.choose(Outcome.Exile, cards, target, game);
                Card card = cards.get(target.getFirstTarget(), game);
                cards.remove(card);
                opponent.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY);
                if (cards.size() == 1) {
                    card = cards.get(cards.iterator().next(), game);
                    opponent.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, true, false);
                }
            }
            return true;
        }
        return false;
    }
}
