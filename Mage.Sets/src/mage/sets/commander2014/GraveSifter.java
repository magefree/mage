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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class GraveSifter extends CardImpl {

    public GraveSifter(UUID ownerId) {
        super(ownerId, 44, "Grave Sifter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.expansionSetCode = "C14";
        this.subtype.add("Elemental");
        this.subtype.add("Beast");

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // When Grave Sifter enters the battlefield, each player chooses a creature type and returns any number of cards of that type from his or her graveyard to his or her hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GraveSifterEffect(), false));
    }

    public GraveSifter(final GraveSifter card) {
        super(card);
    }

    @Override
    public GraveSifter copy() {
        return new GraveSifter(this);
    }
}

class GraveSifterEffect extends OneShotEffect {

    public GraveSifterEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "each player chooses a creature type and returns any number of cards of that type from his or her graveyard to his or her hand";
    }

    public GraveSifterEffect(final GraveSifterEffect effect) {
        super(effect);
    }

    @Override
    public GraveSifterEffect copy() {
        return new GraveSifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Choice typeChoice = new ChoiceImpl(true);
        typeChoice.setMessage("Choose creature type to return cards from your graveyard");
        typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    typeChoice.clearChoice();
                    if (player.choose(outcome, typeChoice, game)) {
                        game.informPlayers(player.getLogName() + " has chosen: " + typeChoice.getChoice());
                        FilterCard filter = new FilterCreatureCard("creature cards with creature type " + typeChoice.getChoice()+ " from your graveyard");
                        filter.add(new SubtypePredicate(typeChoice.getChoice()));
                        Target target = new TargetCardInYourGraveyard(0,Integer.MAX_VALUE, filter);
                        player.chooseTarget(outcome, target, source, game);
                        for (UUID cardId: target.getTargets()) {
                            Card card = game.getCard(cardId);
                            if (card !=null) {
                                player.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD);
                            }
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }
}
