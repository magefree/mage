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
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author L_J
 */
public class EntrailsFeaster extends CardImpl {

    public EntrailsFeaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may exile a creature card from a graveyard. If you do, put a +1/+1 counter on Entrails Feaster. If you don't, tap Entrails Feaster.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new EntrailsFeasterEffect(), TargetController.YOU, false);
        this.addAbility(ability);

    }

    public EntrailsFeaster(final EntrailsFeaster card) {
        super(card);
    }

    @Override
    public EntrailsFeaster copy() {
        return new EntrailsFeaster(this);
    }
}

class EntrailsFeasterEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card from a graveyard");

    public EntrailsFeasterEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may exile a creature card from a graveyard. If you do, put a +1/+1 counter on {this}. If you don't, tap {this}";
    }

    public EntrailsFeasterEffect(final EntrailsFeasterEffect effect) {
        super(effect);
    }

    @Override
    public EntrailsFeasterEffect copy() {
        return new EntrailsFeasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && source.getSourceId() != null) {
            Permanent sourceObject = (Permanent) source.getSourceObjectIfItStillExists(game);
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            target.setNotTarget(true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game) && controller.chooseUse(outcome, "Exile a creature card from a graveyard?", source, game)) {
                if (controller.choose(Outcome.Exile, target, source.getId(), game)) {
                    Card cardChosen = game.getCard(target.getFirstTarget());
                    if (cardChosen != null) {
                        controller.moveCardsToExile(cardChosen, source, game, true, null, "");
                        if (sourceObject != null) {
                            sourceObject.getCounters(game).addCounter(CounterType.P1P1.createInstance());
                            game.informPlayers(controller.getLogName() + " puts a +1/+1 counter on " + sourceObject.getLogName());
                        }
                    }
                } else if (sourceObject != null) {
                    sourceObject.tap(game);
                }
            } else if (sourceObject != null) {
                sourceObject.tap(game);
            }
            return true;
        }
        return false;
    }
}
