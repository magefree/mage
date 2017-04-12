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
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class LinSivviDefiantHero extends CardImpl {

    private static final FilterCard filter = new FilterCard("Rebel card from your graveyard");

    static {
        filter.add(new OwnerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate("Rebel"));
    }

    static final String rule = "Put target Rebel card from your graveyard on the bottom of your library";

    public LinSivviDefiantHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Rebel");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {X}, {tap}: Search your library for a Rebel permanent card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new LinSivviDefiantHeroEffect(),
                new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}: Put target Rebel card from your graveyard on the bottom of your library.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(false, rule), new GenericManaCost(3));
        ability.addTarget(new TargetCardInYourGraveyard(1, filter));
        this.addAbility(ability);
    }

    public LinSivviDefiantHero(final LinSivviDefiantHero card) {
        super(card);
    }

    @Override
    public LinSivviDefiantHero copy() {
        return new LinSivviDefiantHero(this);
    }
}

class LinSivviDefiantHeroEffect extends OneShotEffect {

    public LinSivviDefiantHeroEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for a Rebel permanent card with converted mana cost X or less and put it onto the battlefield. Then shuffle your library";
    }

    public LinSivviDefiantHeroEffect(final LinSivviDefiantHeroEffect effect) {
        super(effect);
    }

    @Override
    public LinSivviDefiantHeroEffect copy() {
        return new LinSivviDefiantHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int xCost = source.getManaCostsToPay().getX();

        FilterPermanentCard filter = new FilterPermanentCard(new StringBuilder("Rebel permanent card with converted mana cost ").append(xCost).append(" or less").toString());
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xCost + 1));
        filter.add(new SubtypePredicate("Rebel"));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);

        if (controller.searchLibrary(target, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }
}
