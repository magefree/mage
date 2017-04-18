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

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ScourgeOfFleets extends CardImpl {

    public ScourgeOfFleets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add("Kraken");

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Scourge of Fleets enters the battlefield, return each creature your opponents control with toughness X or less, where X is the number of Islands you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScourgeOfFleetsEffect(), false));
    }

    public ScourgeOfFleets(final ScourgeOfFleets card) {
        super(card);
    }

    @Override
    public ScourgeOfFleets copy() {
        return new ScourgeOfFleets(this);
    }
}

class ScourgeOfFleetsEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("number of Islands you control");

    static {
        filter.add(new SubtypePredicate("Island"));
    }

    public ScourgeOfFleetsEffect() {
        super(Outcome.Benefit);
        this.staticText = "return each creature your opponents control with toughness X or less, where X is the number of Islands you control";
    }

    public ScourgeOfFleetsEffect(final ScourgeOfFleetsEffect effect) {
        super(effect);
    }

    @Override
    public ScourgeOfFleetsEffect copy() {
        return new ScourgeOfFleetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int islands = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
            FilterPermanent creatureFilter = new FilterCreaturePermanent();
            creatureFilter.add(new ControllerPredicate(TargetController.OPPONENT));
            creatureFilter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, islands + 1));
            Cards cardsToHand = new CardsImpl();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(creatureFilter, source.getControllerId(), source.getSourceId(), game)) {
                cardsToHand.add(permanent);
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
