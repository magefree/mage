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
package mage.sets.coldsnap;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class MartyrOfSands extends CardImpl<MartyrOfSands> {

    private static final FilterCard filter = new FilterCard("X white cards from your hand");
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public MartyrOfSands(UUID ownerId) {
        super(ownerId, 15, "Martyr of Sands", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "CSP";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, Reveal X white cards from your hand, Sacrifice Martyr of Sands: You gain three times X life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MartyrOfSandsEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0,Integer.MAX_VALUE, filter)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public MartyrOfSands(final MartyrOfSands card) {
        super(card);
    }

    @Override
    public MartyrOfSands copy() {
        return new MartyrOfSands(this);
    }
}

class MartyrOfSandsEffect extends OneShotEffect<MartyrOfSandsEffect> {

    public MartyrOfSandsEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain three times X life";
    }

    public MartyrOfSandsEffect(final MartyrOfSandsEffect effect) {
        super(effect);
    }

    @Override
    public MartyrOfSandsEffect copy() {
        return new MartyrOfSandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost revealCost = null;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof RevealTargetFromHandCost) {
                revealCost = (RevealTargetFromHandCost) cost;
            }
        }
        
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null && revealCost != null) {
            int live = revealCost.getNumberRevealedCards() * 3;
            if (live > 0) {
                controller.gainLife(live, game);
            }
            game.informPlayers(new StringBuilder(sourceCard.getName())
                    .append(": ").append(controller.getName()).append(" revealed ")
                    .append(revealCost.getNumberRevealedCards()).append(revealCost.getNumberRevealedCards() == 1 ?"white card":"white cards")
                    .append(" and gained ").append(live).append(" live").toString());
            return true;
        }
        return false;
    }
}
