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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class NamelessRace extends CardImpl {

    public NamelessRace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As Nameless Race enters the battlefield, pay any amount of life. The amount you pay can't be more than the total number of white nontoken permanents your opponents control plus the total number of white cards in their graveyards.
        this.addAbility(new AsEntersBattlefieldAbility(new NamelessRaceEffect()));
        
        // Nameless Race's power and toughness are each equal to the life paid as it entered the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("{this}'s power and toughness are each equal to the life paid as it entered the battlefield")));
    }

    public NamelessRace(final NamelessRace card) {
        super(card);
    }

    @Override
    public NamelessRace copy() {
        return new NamelessRace(this);
    }
}

class NamelessRaceEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterPermanent("white nontoken permanents your opponents control");
    private static final FilterCard filter2 = new FilterCard("white cards in their graveyards");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter2.add(new ColorPredicate(ObjectColor.WHITE));
        filter2.add(new OwnerPredicate(TargetController.OPPONENT));
    }

    public NamelessRaceEffect() {
        super(Outcome.LoseLife);
        staticText = "pay any amount of life. The amount you pay can't be more than the total number of white nontoken permanents your opponents control plus the total number of white cards in their graveyards";
    }

    public NamelessRaceEffect(final NamelessRaceEffect effect) {
        super(effect);
    }

    @Override
    public NamelessRaceEffect copy() {
        return new NamelessRaceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card sourceCard = game.getCard(source.getSourceId());
            int permanentsInPlay = new PermanentsOnBattlefieldCount(filter).calculate(game, source, null);
            int cardsInGraveyards = new CardsInAllGraveyardsCount(filter2).calculate(game, source, null);
            int maxAmount = Math.min(permanentsInPlay + cardsInGraveyards, controller.getLife());
            int payAmount = controller.getAmount(0, maxAmount, "Pay up to " + maxAmount + " life", game);
            controller.loseLife(payAmount, game, false);
            game.informPlayers(new StringBuilder(sourceCard.getLogName()).append(": ").append(controller.getLogName())
                .append(" pays ").append(payAmount).append(" life").toString());
            game.addEffect(new SetPowerToughnessSourceEffect(payAmount, payAmount, Duration.Custom, SubLayer.SetPT_7b), source);
            return true;
        }
        return false;
    }
}
