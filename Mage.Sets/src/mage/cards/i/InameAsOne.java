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
package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class InameAsOne extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Spirit permanent card");

    static {
        filter.add(new SubtypePredicate(SubType.SPIRIT));
    }

    public InameAsOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{B}{B}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Spirit");
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Iname as One enters the battlefield, if you cast it from your hand, you may search your library for a Spirit permanent card, put it onto the battlefield, then shuffle your library.
        this.addAbility(new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, filter)), true),
                CastFromHandSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, you may search your library for a Spirit permanent card, put it onto the battlefield, then shuffle your library."),
                new CastFromHandWatcher());

        // When Iname as One dies, you may exile it. If you do, return target Spirit permanent card from your graveyard to the battlefield.
        Ability ability = new DiesTriggeredAbility(new InameAsOneEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public InameAsOne(final InameAsOne card) {
        super(card);
    }

    @Override
    public InameAsOne copy() {
        return new InameAsOne(this);
    }
}

class InameAsOneEffect extends OneShotEffect {

    public InameAsOneEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile it. If you do, return target Spirit permanent card from your graveyard to the battlefield";
    }

    public InameAsOneEffect(final InameAsOneEffect effect) {
        super(effect);
    }

    @Override
    public InameAsOneEffect copy() {
        return new InameAsOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller != null && sourceObject != null && targetCard != null) {
            if (controller.chooseUse(outcome, "Exile " + sourceObject.getLogName() + " to return Spirit card?", source, game)) {
                // In a Commander game, you may send Iname to the Command Zone instead of exiling it during the resolution
                // of its ability. If you do, its ability still works. Iname's ability only requires that you attempted to
                // exile it, not that it actually gets to the exile zone. This is similar to how destroying a creature
                // (with, for example, Rest in Peace) doesn't necessarily ensure that creature will end up in the graveyard;
                // it just so happens that the action of exiling something and the exile zone both use the same word: "exile".
                Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
                effect.setTargetPointer(new FixedTarget(targetCard.getId(), targetCard.getZoneChangeCounter(game)));
                new ExileSourceEffect().apply(game, source);
                return effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
