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
package mage.sets.anthologyjacevschandra;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public class TerrainGenerator extends CardImpl {

    public TerrainGenerator(UUID ownerId) {
        super(ownerId, 29, "Terrain Generator", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "DD3";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        
        // {2}, {tap}: You may put a basic land card from your hand onto the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutLandOnBattlefieldEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public TerrainGenerator(final TerrainGenerator card) {
        super(card);
    }

    @Override
    public TerrainGenerator copy() {
        return new TerrainGenerator(this);
    }
}

class PutLandOnBattlefieldEffect extends OneShotEffect {
    
    private static final FilterCard filter = new FilterCard("card other than a basic land card");

    static {
        filter.add(Predicates.and(new CardTypePredicate(CardType.LAND), new SupertypePredicate("Basic")));
    }

    private static final String choiceText = "Put a basic land card from your hand onto the battlefield?";

    public PutLandOnBattlefieldEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "put a basic land card from your hand onto the battlefield";
    }

    public PutLandOnBattlefieldEffect(final PutLandOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public PutLandOnBattlefieldEffect copy() {
        return new PutLandOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.PutLandInPlay, choiceText, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(Outcome.PutLandInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                card.putOntoBattlefield(game, Zone.HAND, source.getSourceId(), source.getControllerId());
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                        permanent.setTapped(true);
                }
                return true;
            }
        }
        return false;
    }
}