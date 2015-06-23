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
package mage.sets.magic2014;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Library;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class ChandraPyromaster extends CardImpl {

    public ChandraPyromaster(UUID ownerId) {
        super(ownerId, 132, "Chandra, Pyromaster", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");
        this.expansionSetCode = "M14";
        this.subtype.add("Chandra");


        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Chandra, Pyromaster deals 1 damage to target player and 1 damage to up to one target creature that player controls. That creature can't block this turn.
        LoyaltyAbility ability1 = new LoyaltyAbility(new ChandraPyromasterEffect1(), 1);
        Target target1 = new TargetPlayer();
        ability1.addTarget(target1);
        ability1.addTarget(new ChandraPyromasterTarget());
        this.addAbility(ability1);

        // 0: Exile the top card of your library. You may play it this turn.
        LoyaltyAbility ability2 = new LoyaltyAbility(new ChandraPyromasterEffect2(), 0);
        this.addAbility(ability2);

        // -7: Exile the top ten cards of your library. Choose an instant or sorcery card exiled this way and copy it three times. You may cast the copies without paying their mana costs.
        LoyaltyAbility ability3 = new LoyaltyAbility(new ChandraPyromasterEffect3(), -7);
        this.addAbility(ability3);

    }

    public ChandraPyromaster(final ChandraPyromaster card) {
        super(card);
    }

    @Override
    public ChandraPyromaster copy() {
        return new ChandraPyromaster(this);
    }
}

class ChandraPyromasterEffect1 extends OneShotEffect {

    public ChandraPyromasterEffect1() {
        super(Outcome.Damage);
        staticText = "{this} deals 1 damage to target player and 1 damage to up to one target creature that player controls. That creature can't block this turn";
    }

    public ChandraPyromasterEffect1(final ChandraPyromasterEffect1 effect) {
        super(effect);
    }

    @Override
    public ChandraPyromasterEffect1 copy() {
        return new ChandraPyromasterEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (player != null) {
            player.damage(1, source.getSourceId(), game, false, true);
        }
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            creature.damage(1, source.getSourceId(), game, false, true);
            ContinuousEffect effect = new CantBlockTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature.getId()));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class ChandraPyromasterTarget extends TargetPermanent {

    public ChandraPyromasterTarget() {
        super(0, 1, new FilterCreaturePermanent("creature that the targeted player controls"), false);
    }

    public ChandraPyromasterTarget(final ChandraPyromasterTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = source.getFirstTarget();
        Permanent permanent = game.getPermanent(id);
        if (firstTarget != null && permanent != null && permanent.getControllerId().equals(firstTarget)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(sourceId);

        for (StackObject item : game.getState().getStack()) {
            if (item.getId().equals(sourceId)) {
                object = item;
            }
            if (item.getSourceId().equals(sourceId)) {
                object = item;
            }
        }

        if (object instanceof StackObject) {
            UUID playerId = ((StackObject) object).getStackAbility().getFirstTarget();
            for (UUID targetId : availablePossibleTargets) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && permanent.getControllerId().equals(playerId)) {
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public ChandraPyromasterTarget copy() {
        return new ChandraPyromasterTarget(this);
    }
}

class ChandraPyromasterEffect2 extends OneShotEffect {

    public ChandraPyromasterEffect2() {
        super(Outcome.Detriment);
        this.staticText = "Exile the top card of your library. You may play it this turn";
    }

    public ChandraPyromasterEffect2(final ChandraPyromasterEffect2 effect) {
        super(effect);
    }

    @Override
    public ChandraPyromasterEffect2 copy() {
        return new ChandraPyromasterEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null && controller.getLibrary().size() > 0) {
            Library library = controller.getLibrary();
            Card card = library.removeFromTop(game);
            if (card != null) {
                controller.moveCardToExileWithInfo(card, source.getSourceId(), sourceObject.getIdName() + " <this card may be played the turn it was exiled>", source.getSourceId(), game, Zone.LIBRARY, true);
                ContinuousEffect effect = new ChandraPyromasterCastFromExileEffect(); 
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class ChandraPyromasterCastFromExileEffect extends AsThoughEffectImpl {

    public ChandraPyromasterCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile this turn";
    }

    public ChandraPyromasterCastFromExileEffect(final ChandraPyromasterCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ChandraPyromasterCastFromExileEffect copy() {
        return new ChandraPyromasterCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (targetPointer.getTargets(game, source).contains(sourceId)) {
            return game.getState().getZone(sourceId).equals(Zone.EXILED);
        }
        return false;
    }
}

class ChandraPyromasterEffect3 extends OneShotEffect {

    public ChandraPyromasterEffect3() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Exile the top ten cards of your library. Choose an instant or sorcery card exiled this way and copy it three times. You may cast the copies without paying their mana costs";
    }

    public ChandraPyromasterEffect3(final ChandraPyromasterEffect3 effect) {
        super(effect);
    }

    @Override
    public ChandraPyromasterEffect3 copy() {
        return new ChandraPyromasterEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        int max = Math.min(you.getLibrary().size(), 10);
        for (int i = 0; i < max; i++) {
            Card card = you.getLibrary().removeFromTop(game);
            if (card != null) {
                card.moveToExile(source.getSourceId(), "Chandra Pyromaster", source.getSourceId(), game);
                cards.add(card);
            }
        }

        if (cards.getCards(new FilterInstantOrSorceryCard(), game).size() > 0) {
            TargetCard target = new TargetCard(Zone.EXILED, new FilterInstantOrSorceryCard());
            if (you.chooseTarget(Outcome.PlayForFree, cards, target, source, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    Card copy1 = card.copy();
                    Card copy2 = card.copy();
                    Card copy3 = card.copy();
                    if (copy1 != null && you.chooseUse(outcome, "Do you wish to cast copy 1 of " + card.getName(), game)) {
                        you.cast(copy1.getSpellAbility(), game, true);
                    }
                    if (copy2 != null && you.chooseUse(outcome, "Do you wish to cast copy 2 of " + card.getName(), game)) {
                        you.cast(copy2.getSpellAbility(), game, true);
                    }
                    if (copy3 != null && you.chooseUse(outcome, "Do you wish to cast copy 3 of " + card.getName(), game)) {
                        you.cast(copy3.getSpellAbility(), game, true);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
