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
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class DarkIntimations extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Bolas planeswalker spell");

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new SubtypePredicate(SubType.BOLAS));
    }

    public DarkIntimations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}{R}");

        // Each opponent sacrifices a creature or planeswalker, then discards a card. You return a creature or planeswalker card from your graveyard to your hand, then draw a card.
        this.getSpellAbility().addEffect(new DarkIntimationsEffect());

        // When you cast a Bolas planeswalker spell, exile Dark Intimations from your graveyard. That planeswalker enters the battlefield with an additional loyalty counter on it.
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.GRAVEYARD, new DarkIntimationsGraveyardEffect(), filter, false, true));
    }

    public DarkIntimations(final DarkIntimations card) {
        super(card);
    }

    @Override
    public DarkIntimations copy() {
        return new DarkIntimations(this);
    }
}

class DarkIntimationsEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("a creature or planeswalker");
    private static final FilterCard filterCard = new FilterCard("a creature or planeswalker card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)));
        filterCard.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public DarkIntimationsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent sacrifices a creature or planeswalker, then discards a card. You return a creature or planeswalker card from your graveyard to your hand, then draw a card";
    }

    public DarkIntimationsEffect(final DarkIntimationsEffect effect) {
        super(effect);
    }

    @Override
    public DarkIntimationsEffect copy() {
        return new DarkIntimationsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<UUID> perms = new ArrayList<>();
        filter.add(new ControllerPredicate(TargetController.YOU));
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetPermanent target = new TargetPermanent(1, 1, filter, true);
                if (target.canChoose(player.getId(), game)) {
                    player.chooseTarget(Outcome.Sacrifice, target, source, game);
                    perms.addAll(target.getTargets());
                }
            }
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.discardOne(false, source, game);
            }
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filterCard);
        if (target.canChoose(source.getSourceId(), source.getControllerId(), game) 
                && controller.choose(Outcome.ReturnToHand, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card == null) {
                return false;
            }
            controller.moveCards(card, Zone.HAND, source, game);
        }
        controller.drawCards(1, game);
        return true;
    }
}

class DarkIntimationsGraveyardEffect extends OneShotEffect {

    public DarkIntimationsGraveyardEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile {this} from your graveyard. That planeswalker enters the battlefield with an additional loyalty counter on it";
    }

    public DarkIntimationsGraveyardEffect(final DarkIntimationsGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public DarkIntimationsGraveyardEffect copy() {
        return new DarkIntimationsGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card sourceCard = controller.getGraveyard().get(source.getSourceId(), game);
            if (sourceCard != null) {
                controller.moveCards(sourceCard, Zone.EXILED, source, game);
            }
            Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (spell != null) {
                ContinuousEffect effect = new DarkIntimationsReplacementEffect();
                effect.setTargetPointer(new FixedTarget(spell.getSourceId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class DarkIntimationsReplacementEffect extends ReplacementEffectImpl {

    DarkIntimationsReplacementEffect() {
        super(Duration.OneUse, Outcome.Benefit);
        staticText = "That planeswalker enters the battlefield with an additional loyalty counter on it";
    }

    DarkIntimationsReplacementEffect(DarkIntimationsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null 
                && event.getTargetId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.LOYALTY.createInstance(), source, game);
        }
        return false;
    }

    @Override
    public DarkIntimationsReplacementEffect copy() {
        return new DarkIntimationsReplacementEffect(this);
    }
}
