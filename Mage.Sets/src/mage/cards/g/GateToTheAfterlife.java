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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class GateToTheAfterlife extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a nontoken creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public GateToTheAfterlife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a nontoken creature you control dies, you gain 1 life. Then you may draw a card. If you do, discard a card.
        Ability ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(new GainLifeEffect(1), false, filter, false, true);
        Effect effect = new DrawDiscardControllerEffect(1, 1, true);
        effect.setText("Then you may draw a card. If you do, discard a card");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {2}, {T}, Sacrifice Gate to the Afterlife: Search your graveyard, hand, and/or library for a card named God-Pharaoh's Gift and put it onto the battlefield. If you seearch your library this way, shuffle it. Activate this ability only if there are six or more creature cards in your graveyard.
        ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new GateToTheAfterlifeEffect(), new GenericManaCost(2), new CardsInControllerGraveCondition(6, new FilterCreatureCard()));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public GateToTheAfterlife(final GateToTheAfterlife card) {
        super(card);
    }

    @Override
    public GateToTheAfterlife copy() {
        return new GateToTheAfterlife(this);
    }
}

class GateToTheAfterlifeEffect extends OneShotEffect {

    static private String cardName = "God-Pharaoh's Gift";

    public GateToTheAfterlifeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your graveyard, hand, and/or library for a card named "
                + cardName
                + " and put it onto the battlefield. If you search your library this way, shuffle it";
    }

    public GateToTheAfterlifeEffect(final GateToTheAfterlifeEffect effect) {
        super(effect);
    }

    @Override
    public GateToTheAfterlifeEffect copy() {
        return new GateToTheAfterlifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card named " + cardName);
        filter.add(new NamePredicate(cardName));
        Card card = null;
        // Graveyard check
        if (controller.chooseUse(Outcome.Benefit, "Do you want to search your graveyard for " + cardName + "?", source, game)) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }
        // Hand check
        if (card == null && controller.chooseUse(Outcome.Benefit, "Do you want to search your hand for " + cardName + "?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }
        // Library check
        if (card == null && controller.chooseUse(Outcome.Benefit, "Do you want to search your library for " + cardName + "?", source, game)) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, game)) {
                card = game.getCard(target.getFirstTarget());
            }
            controller.shuffleLibrary(source, game);
        }
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
