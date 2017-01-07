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
package mage.cards.a;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author fireshoes
 */
public class AjanisAid extends CardImpl {

    public AjanisAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}");

        // When Ajani's Aid enters the battlefield, you may search your library and/or graveyard for a card named Ajani, Valiant Protector, reveal it,
        // and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AjanisAidEffect()));

        // Sacrifice Ajani's Aid: Prevent all combat damage a creature of your choice would deal this turn.
        Effect effect = new PreventNextDamageFromChosenSourceToYouEffect(Duration.EndOfTurn, new FilterCreaturePermanent("creature of your choice"), true);
        effect.setText("Prevent all combat damage a creature of your choice would deal this turn");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new SacrificeSourceCost()));
    }

    public AjanisAid(final AjanisAid card) {
        super(card);
    }

    @Override
    public AjanisAid copy() {
        return new AjanisAid(this);
    }
}

class AjanisAidEffect extends OneShotEffect {

    public AjanisAidEffect() {
        super(Outcome.Benefit);
        staticText = "You may search your library and/or graveyard for a card named Ajani, Valiant Protector, reveal it, and put it into your hand. "
                + "If you search your library this way, shuffle it";
    }

    public AjanisAidEffect(final AjanisAidEffect effect) {
        super(effect);
    }

    @Override
    public AjanisAidEffect copy() {
        return new AjanisAidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null && controller.chooseUse(outcome, "Search your library and/or graveyard for a card named Ajani, Valiant Protector?", source, game)) {
            //Search your library and graveyard
            Cards allCards = new CardsImpl();
            boolean librarySearched = false;
            if (controller.chooseUse(outcome, "Search also your library?", source, game)) {
                librarySearched = true;
                allCards.addAll(controller.getLibrary().getCardList());
            }
            allCards.addAll(controller.getGraveyard());
            FilterCard filter = new FilterCard("a card named Ajani, Valiant Protector");
            filter.add(new NamePredicate("Ajani, Valiant Protector"));
            TargetCard target = new TargetCard(0, 1, Zone.ALL, new FilterCard());
            if (controller.choose(outcome, allCards, target, game)) {
                Card cardFound = game.getCard(target.getFirstTarget());
                if (cardFound != null) {
                    controller.revealCards(sourceObject.getIdName(), new CardsImpl(cardFound), game);
                    controller.moveCards(cardFound, Zone.HAND, source, game);
                }
            }
            if (librarySearched) {
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
