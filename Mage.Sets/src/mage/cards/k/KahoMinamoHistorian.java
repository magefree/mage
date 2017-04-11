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
package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.SearchEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class KahoMinamoHistorian extends CardImpl {

    public KahoMinamoHistorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Kaho, Minamo Historian enters the battlefield, search your library for up to three instant cards and exile them. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KahoMinamoHistorianEffect(), false));

        // {X}, {tap}: You may cast a card with converted mana cost X exiled with Kaho without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KahoMinamoHistorianCastEffect(), new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public KahoMinamoHistorian(final KahoMinamoHistorian card) {
        super(card);
    }

    @Override
    public KahoMinamoHistorian copy() {
        return new KahoMinamoHistorian(this);
    }
}

class KahoMinamoHistorianEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard("up to three instant cards");

    static {
        filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public KahoMinamoHistorianEffect() {
        super(new TargetCardInLibrary(0, 3, filter), Outcome.Benefit);
        this.staticText = "search your library for up to three instant cards and exile them. Then shuffle your library";
    }

    public KahoMinamoHistorianEffect(final KahoMinamoHistorianEffect effect) {
        super(effect);
    }

    @Override
    public KahoMinamoHistorianEffect copy() {
        return new KahoMinamoHistorianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            if (controller.searchLibrary(target, game)) {
                UUID exileZone = CardUtil.getCardExileZoneId(game, source);
                if (!target.getTargets().isEmpty()) {
                    controller.moveCardsToExile(new CardsImpl(target.getTargets()).getCards(game), source, game, true, exileZone, sourceObject.getIdName());
                }
            }
            controller.shuffleLibrary(source, game);
            return true;

        }
        return false;
    }
}

class KahoMinamoHistorianCastEffect extends OneShotEffect {

    public KahoMinamoHistorianCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast a card with converted mana cost X exiled with {this} without paying its mana cost";
    }

    public KahoMinamoHistorianCastEffect(final KahoMinamoHistorianCastEffect effect) {
        super(effect);
    }

    @Override
    public KahoMinamoHistorianCastEffect copy() {
        return new KahoMinamoHistorianCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCard filter = new FilterCard();
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, source.getManaCostsToPay().getX()));
            TargetCardInExile target = new TargetCardInExile(filter, CardUtil.getCardExileZoneId(game, source));
            Cards cards = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            if (!cards.isEmpty() && controller.choose(Outcome.PlayForFree, cards, target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.cast(card.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
