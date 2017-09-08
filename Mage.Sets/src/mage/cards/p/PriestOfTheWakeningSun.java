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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public class PriestOfTheWakeningSun extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dinosaur card");

    static {
        filter.add(new SubtypePredicate(SubType.DINOSAUR));
    }

    public PriestOfTheWakeningSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may reveal a Dinosaur card from your hand. If you do, you gain 2 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new PriestOfTheWakeningSunEffect(), TargetController.YOU, true);
        this.addAbility(ability);

        // {3}{W}{W}, Sacrifice Priest of the Wakening Sun: Search your library for a Dinosaur card, reveal it, put it into your hand, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(target), true, true), new ManaCostsImpl("{3}{W}{W}"));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    public PriestOfTheWakeningSun(final PriestOfTheWakeningSun card) {
        super(card);
    }

    @Override
    public PriestOfTheWakeningSun copy() {
        return new PriestOfTheWakeningSun(this);
    }
}

class PriestOfTheWakeningSunEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a Dinosaur card to reveal");

    static {
        filter.add(new SubtypePredicate(SubType.DINOSAUR));
    }

    PriestOfTheWakeningSunEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal a Dinosaur card from your hand. If you do, you gain 2 life";
    }

    PriestOfTheWakeningSunEffect(final PriestOfTheWakeningSunEffect effect) {
        super(effect);
    }

    @Override
    public PriestOfTheWakeningSunEffect copy() {
        return new PriestOfTheWakeningSunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());

        if (controller != null && sourceObject != null) {
            if (controller.getHand().count(filter, source.getSourceId(), source.getControllerId(), game) > 0) {
                if (controller.chooseUse(outcome, "Reveal a Dinosaur card?", source, game)) {
                    TargetCardInHand target = new TargetCardInHand(0, 1, filter);
                    if (controller.chooseTarget(outcome, target, source, game) && !target.getTargets().isEmpty()) {
                        Cards cards = new CardsImpl();
                        cards.addAll(target.getTargets());
                        controller.revealCards(sourceObject.getIdName(), cards, game);
                        controller.gainLife(2, game);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
