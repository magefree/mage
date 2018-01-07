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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TwilightProphet extends CardImpl {

    public TwilightProphet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ascend
        this.addAbility(new AscendAbility());

        // At the beginning of your upkeep, if you have the city's blessing, reveal the top card of your library and put it into your hand.
        // Each opponent loses X life and you gain X life, where X is that card's converted mana cost.
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(
                new TwilightProphetEffect(), TargetController.YOU, false), CitysBlessingCondition.instance,
                "At the beginning of your upkeep, if you have the city's blessing, reveal the top card of your library and put it into your hand."
                + "Each opponent loses X life and you gain X life, where X is that card's converted mana cost."));

    }

    public TwilightProphet(final TwilightProphet card) {
        super(card);
    }

    @Override
    public TwilightProphet copy() {
        return new TwilightProphet(this);
    }
}

class TwilightProphetEffect extends OneShotEffect {

    public TwilightProphetEffect() {
        super(Outcome.Benefit);
        this.staticText = "if you have the city's blessing, reveal the top card of your library and put it into your hand. "
                + "Each opponent loses X life and you gain X life, where X is that card's converted mana cost.";
    }

    public TwilightProphetEffect(final TwilightProphetEffect effect) {
        super(effect);
    }

    @Override
    public TwilightProphetEffect copy() {
        return new TwilightProphetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                controller.moveCards(card, Zone.HAND, source, game);
                game.applyEffects();
                int amount = card.getConvertedManaCost();
                if (amount > 0) {
                    new LoseLifeOpponentsEffect(amount).apply(game, source);
                    controller.gainLife(amount, game);
                }
            }
            return true;
        }
        return false;
    }
}
