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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class StonehewerGiant extends CardImpl<StonehewerGiant> {

    public StonehewerGiant(UUID ownerId) {
        super(ownerId, 31, "Stonehewer Giant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Giant");
        this.subtype.add("Warrior");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {1}{W}, {tap}: Search your library for an Equipment card and put it onto the battlefield. Attach it to a creature you control. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StonehewerGiantEffect(), new ManaCostsImpl("{1}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public StonehewerGiant(final StonehewerGiant card) {
        super(card);
    }

    @Override
    public StonehewerGiant copy() {
        return new StonehewerGiant(this);
    }
}

class StonehewerGiantEffect extends OneShotEffect<StonehewerGiantEffect> {

    public StonehewerGiantEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search your library for an Equipment card and put it onto the battlefield. Attach it to a creature you control. Then shuffle your library";
    }

    public StonehewerGiantEffect(final StonehewerGiantEffect effect) {
        super(effect);
    }

    @Override
    public StonehewerGiantEffect copy() {
        return new StonehewerGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        FilterCard filter = new FilterCard("Equipment");
        filter.add(new SubtypePredicate("Equipment"));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), source.getControllerId());
                Permanent equipment = game.getPermanent(card.getId());

                Target targetCreature = new TargetControlledCreaturePermanent();
                if (equipment != null && player.choose(Outcome.BoostCreature, targetCreature, source.getSourceId(), game)) {
                    Permanent permanent = game.getPermanent(targetCreature.getFirstTarget());
                    permanent.addAttachment(equipment.getId(), game);
                }
            }
        }
        player.shuffleLibrary(game);
        return true;
    }
}