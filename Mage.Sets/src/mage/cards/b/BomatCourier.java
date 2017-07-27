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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class BomatCourier extends CardImpl {

    public BomatCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Bomat Courier attacks, exile the top card of your library face down.
        this.addAbility(new AttacksTriggeredAbility(new BomatCourierExileEffect(), false));

        // {R}, Discard your hand, Sacrifice Bomat Courier: Put all cards exiled with Bomat Courier into their owners' hands.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BomatCourierReturnEffect(), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new DiscardHandCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public BomatCourier(final BomatCourier card) {
        super(card);
    }

    @Override
    public BomatCourier copy() {
        return new BomatCourier(this);
    }
}

class BomatCourierExileEffect extends OneShotEffect {

    BomatCourierExileEffect() {
        super(Outcome.Exile);
        this.staticText = "exile the top card of your library face down";
    }

    BomatCourierExileEffect(final BomatCourierExileEffect effect) {
        super(effect);
    }

    @Override
    public BomatCourierExileEffect copy() {
        return new BomatCourierExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
                card.setFaceDown(true, game);
                controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName());
                card.setFaceDown(true, game);
                return true;
            }
        }
        return false;
    }
}

class BomatCourierReturnEffect extends OneShotEffect {

    BomatCourierReturnEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Put all cards exiled with {this} into their owners' hands";
    }

    BomatCourierReturnEffect(final BomatCourierReturnEffect effect) {
        super(effect);
    }

    @Override
    public BomatCourierReturnEffect copy() {
        return new BomatCourierReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), 0));
            if (exileZone != null) {
                controller.moveCards(exileZone, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
