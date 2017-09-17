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
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.a.AzcantaTheSunkenRuin;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SearchForAzcanta extends CardImpl {

    public SearchForAzcanta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.transformable = true;
        this.secondSideCardClazz = AzcantaTheSunkenRuin.class;

        this.addSuperType(SuperType.LEGENDARY);

        // At the beginning of your upkeep, look at the top card of your library. You may put it into your graveyard. Then if you have seven or more cards in your graveyard, you may transform Search for Azcanta.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SearchForAzcantaLookLibraryEffect(), TargetController.YOU, true);
        ability.addEffect(new ConditionalOneShotEffect(new TransformSourceEffect(true), new CardsInControllerGraveCondition(7),
                "Then if you have seven or more cards in your graveyard, you may transform {this}"));
        this.addAbility(ability);
        this.addAbility(new TransformAbility());
    }

    public SearchForAzcanta(final SearchForAzcanta card) {
        super(card);
    }

    @Override
    public SearchForAzcanta copy() {
        return new SearchForAzcanta(this);
    }
}

class SearchForAzcantaLookLibraryEffect extends OneShotEffect {

    public SearchForAzcantaLookLibraryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top card of your library. You may put that card into your graveyard";
    }

    public SearchForAzcantaLookLibraryEffect(final SearchForAzcantaLookLibraryEffect effect) {
        super(effect);
    }

    @Override
    public SearchForAzcantaLookLibraryEffect copy() {
        return new SearchForAzcantaLookLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(card);
                    controller.lookAtCards(sourceObject.getIdName(), cards, game);
                    if (controller.chooseUse(Outcome.Neutral, "Do you wish to put the card into your graveyard?", source, game)) {
                        return controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    }

                }
            }
            return true;
        }
        return false;
    }
}
