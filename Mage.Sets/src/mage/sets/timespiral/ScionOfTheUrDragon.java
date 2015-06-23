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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author duncant
 */

public class ScionOfTheUrDragon extends CardImpl {

    public ScionOfTheUrDragon(UUID ownerId) {
        super(ownerId, 246, "Scion of the Ur-Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        this.expansionSetCode = "TSP";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");
        this.subtype.add("Avatar");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // {2}: Search your library for a Dragon permanent card and put it into your graveyard. If you do, Scion of the Ur-Dragon becomes a copy of that card until end of turn. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                                                   new ScionOfTheUrDragonEffect(),
                                                   new ManaCostsImpl("{2}")));
    }

    public ScionOfTheUrDragon(final ScionOfTheUrDragon card) {
        super(card);
    }

    @Override
    public ScionOfTheUrDragon copy() {
        return new ScionOfTheUrDragon(this);
    }
}

class ScionOfTheUrDragonEffect extends SearchEffect {
    private static final FilterCard filter = new FilterPermanentCard("Dragon permanent card");
    
    static {
        filter.add(new SubtypePredicate("Dragon"));
    }
        
    public ScionOfTheUrDragonEffect() {
        super(new TargetCardInLibrary(filter), Outcome.Copy);
        staticText = "Search your library for a Dragon permanent card and put it into your graveyard. If you do, Scion of the Ur-Dragon becomes a copy of that card until end of turn. Then shuffle your library.";
    }

    ScionOfTheUrDragonEffect(final ScionOfTheUrDragonEffect effect) {
        super(effect);
    }

    @Override
    public ScionOfTheUrDragonEffect copy() {
        return new ScionOfTheUrDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
        if (player != null && sourcePermanent != null) {
            if (player.searchLibrary(target, game)) {
                for (UUID cardId : target.getTargets()) {
                    Card card = player.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        player.moveCards(card, Zone.LIBRARY, Zone.GRAVEYARD, ability, game);
                        CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, card, ability.getSourceId());
                        game.addEffect(copyEffect, ability);
                    }
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
