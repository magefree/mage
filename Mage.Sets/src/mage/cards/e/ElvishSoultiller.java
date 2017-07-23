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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ElvishSoultiller extends CardImpl {

    public ElvishSoultiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add("Elf");
        this.subtype.add("Mutant");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Elvish Soultiller dies, choose a creature type. Shuffle all creature cards of that type from your graveyard into your library.
        addAbility(new DiesTriggeredAbility(new ElvishSoultillerEffect()));

    }

    public ElvishSoultiller(final ElvishSoultiller card) {
        super(card);
    }

    @Override
    public ElvishSoultiller copy() {
        return new ElvishSoultiller(this);
    }
}

class ElvishSoultillerEffect extends OneShotEffect {

    public ElvishSoultillerEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a creature type. Shuffle all creature cards of that type from your graveyard into your library";
    }

    public ElvishSoultillerEffect(final ElvishSoultillerEffect effect) {
        super(effect);
    }

    @Override
    public ElvishSoultillerEffect copy() {
        return new ElvishSoultillerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null && mageObject != null) {
            Choice typeChoice = new ChoiceCreatureType();
            while (!controller.choose(outcome, typeChoice, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + typeChoice.getChoice());
            }
            Cards cardsToLibrary = new CardsImpl();
            FilterCreatureCard filter = new FilterCreatureCard();
            filter.add(new SubtypePredicate(SubType.byDescription(typeChoice.getChoice())));
            cardsToLibrary.addAll(controller.getGraveyard().getCards(filter, source.getSourceId(), source.getControllerId(), game));
            controller.putCardsOnTopOfLibrary(cardsToLibrary, game, source, false);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
