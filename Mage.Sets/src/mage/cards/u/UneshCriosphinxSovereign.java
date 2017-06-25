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
package mage.cards.u;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author spjspj
 */
public class UneshCriosphinxSovereign extends CardImpl {

    private static final FilterCard filter = new FilterCard("Sphinx spells");

    public UneshCriosphinxSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Sphinx");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sphinx spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 2)));

        // Whenever Unesh, Criosphinx Sovereign or another Sphinx enters the battlefield under your control, reveal the top four cards of your library. An opponent seperates those cards into two piles. Put one pile into your hand and the other into your graveyard.
        Ability ability = new UneshCriosphinxSovereignTriggeredAbility();
        this.addAbility(ability);
    }

    public UneshCriosphinxSovereign(final UneshCriosphinxSovereign card) {
        super(card);
    }

    @Override
    public UneshCriosphinxSovereign copy() {
        return new UneshCriosphinxSovereign(this);
    }
}

class UneshCriosphinxSovereignTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new SubtypePredicate(SubType.SPHINX));
    }

    public UneshCriosphinxSovereignTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UneshCriosphinxSovereignEffect(), false);
    }

    public UneshCriosphinxSovereignTriggeredAbility(UneshCriosphinxSovereignTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.getOwnerId().equals(controllerId)
                && permanent.isCreature()
                && filter.match(permanent, game)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever {this} or another Sphinx enters the battlefield under your control, ").append(super.getRule()).toString();
    }

    @Override
    public UneshCriosphinxSovereignTriggeredAbility copy() {
        return new UneshCriosphinxSovereignTriggeredAbility(this);
    }
}

class UneshCriosphinxSovereignEffect extends OneShotEffect {

    public UneshCriosphinxSovereignEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard";
    }

    public UneshCriosphinxSovereignEffect(final UneshCriosphinxSovereignEffect effect) {
        super(effect);
    }

    @Override
    public UneshCriosphinxSovereignEffect copy() {
        return new UneshCriosphinxSovereignEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 4));
        controller.revealCards(sourceObject.getName(), cards, game);

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.isEmpty()) {
            Player opponent = game.getPlayer(opponents.iterator().next());
            TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, new FilterCard("cards to put in the first pile"));
            List<Card> pile1 = new ArrayList<>();
            if (opponent.choose(Outcome.Neutral, cards, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = cards.get(targetId, game);
                    if (card != null) {
                        pile1.add(card);
                        cards.remove(card);
                    }
                }
            }
            List<Card> pile2 = new ArrayList<>();
            pile2.addAll(cards.getCards(game));

            boolean choice = controller.choosePile(outcome, "Choose a pile to put into your hand.", pile1, pile2, game);

            Zone pile1Zone = Zone.GRAVEYARD;
            Zone pile2Zone = Zone.HAND;
            if (choice) {
                pile1Zone = Zone.HAND;
                pile2Zone = Zone.GRAVEYARD;
            }

            StringBuilder sb = new StringBuilder("Pile 1, going to ").append(pile1Zone == Zone.HAND ? "Hand" : "Graveyard").append(": ");
            int i = 0;
            for (Card card : pile1) {
                i++;
                sb.append(card.getName());
                if (i < pile1.size()) {
                    sb.append(", ");
                }
                card.moveToZone(pile1Zone, source.getSourceId(), game, false);
            }
            game.informPlayers(sb.toString());

            sb = new StringBuilder("Pile 2, going to ").append(pile2Zone == Zone.HAND ? "Hand" : "Graveyard").append(':');
            i = 0;
            for (Card card : pile2) {
                i++;
                sb.append(' ').append(card.getName());
                if (i < pile2.size()) {
                    sb.append(", ");
                }
                card.moveToZone(pile2Zone, source.getSourceId(), game, false);
            }
            game.informPlayers(sb.toString());
        }

        return true;
    }
}
