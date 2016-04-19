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
package mage.sets.commander2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public class MerenOfClanNelToth extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MerenOfClanNelToth(UUID ownerId) {
        super(ownerId, 49, "Meren of Clan Nel Toth", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        this.expansionSetCode = "C15";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever another creature you control dies, you get an experience counter.
        Effect effect = new AddCountersControllerEffect(CounterType.EXPERIENCE.createInstance(1), false);
        effect.setText("you get an experience counter");
        this.addAbility(new DiesCreatureTriggeredAbility(effect, false, filter));
                
        // At the beginning of your end step, choose target creature card in your graveyard. 
        // If that card's converted mana cost is less than or equal to the number of experience counters you have, return it to the battlefield. Otherwise, put it into your hand.
        Target target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new MerenOfClanNelTothEffect(), false);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public MerenOfClanNelToth(final MerenOfClanNelToth card) {
        super(card);
    }

    @Override
    public MerenOfClanNelToth copy() {
        return new MerenOfClanNelToth(this);
    }
}

class MerenOfClanNelTothEffect extends OneShotEffect {

    public MerenOfClanNelTothEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "choose target creature card in your graveyard. If that card's converted mana cost is less than or equal to the number of experience counters you have, return it to the battlefield. Otherwise, put it into your hand";
    }

    public MerenOfClanNelTothEffect(final MerenOfClanNelTothEffect effect) {
        super(effect);
    }

    @Override
    public MerenOfClanNelTothEffect copy() {
        return new MerenOfClanNelTothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = player.getCounters().getCount(CounterType.EXPERIENCE);
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                Zone targetZone = Zone.HAND;
                String text = " put into hand of ";
                if (card.getConvertedManaCost() <= amount) {
                    targetZone = Zone.BATTLEFIELD;
                    text = " put onto battlefield for ";
                }
                card.moveToZone(targetZone, source.getSourceId(), game, false);
                game.informPlayers(new StringBuilder("Meren of Clan Nel Toth: ").append(card.getName()).append(text).append(player.getLogName()).toString());
                return true;
            }
        }
        return false;
    }
}
