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
package mage.sets.weatherlight;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class SpinningDarkness extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public SpinningDarkness(UUID ownerId) {
        super(ownerId, 23, "Spinning Darkness", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{B}{B}");
        this.expansionSetCode = "WTH";

        // You may exile the top three black cards of your graveyard rather than pay Spinning Darkness's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SpinningDarknessCost()));
        
        // Spinning Darkness deals 3 damage to target nonblack creature. You gain 3 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public SpinningDarkness(final SpinningDarkness card) {
        super(card);
    }

    @Override
    public SpinningDarkness copy() {
        return new SpinningDarkness(this);
    }
}

class SpinningDarknessCost extends CostImpl {
    
    private static final FilterCard filter = new FilterCard("black card");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    SpinningDarknessCost() {
        this.text = "exile the top three black cards of your graveyard";      
    }

    SpinningDarknessCost(final SpinningDarknessCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            Set<Card> blackCardsInGraveyard = controller.getGraveyard().getCards(filter, game);
            int size = blackCardsInGraveyard.size();
            if (size >= 3) {
                Iterator<Card> it = blackCardsInGraveyard.iterator();
                Cards cardsToExile = new CardsImpl();
                int i = 1;
                while (cardsToExile.size() < 3) {
                    Card card = it.next();
                    if (i > size - 3) {
                        cardsToExile.add(card);
                    }
                    i++;
                }
                paid = controller.moveCards(cardsToExile, Zone.GRAVEYARD, Zone.EXILED, ability, game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            return controller.getGraveyard().getCards(filter, game).size() >= 3;
        }
        return false;
    }

    @Override
    public SpinningDarknessCost copy() {
        return new SpinningDarknessCost(this);
    }
}
