/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities.keyword;

import java.util.UUID;
import mage.Constants.AsThoughEffectType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;


/**
 *
 * 502.59. Suspend
 *
 * 502.59a Suspend is a keyword that represents three abilities. The first is a static
 * ability that functions while the card with suspend is in a player's hand. The second
 * and third are triggered abilities that function in the removed-from-the-game zone.
 * "Suspend N--[cost]" means "If you could play this card from your hand, you may pay
 * [cost] and remove it from the game with N time counters on it. This is a special
 * action that doesn't use the stack," and "At the beginning of your upkeep, if this
 * card is suspended, remove a time counter from it," and "When the last time counter
 * is removed from this card, if it's removed from the game, play it without paying its
 * mana cost if able. If you can't, it remains removed from the game. If you play it
 * this way and it's a creature, it gains haste until you lose control of it."
 *
 * 502.59b A card is "suspended" if it's in the removed-from-the-game zone, has suspend,
 * and has a time counter on it.
 *
 * 502.59c Playing a spell as an effect of its suspend ability follows the rules for
 * paying alternative costs in rules 409.1b and 409.1f-h.
 *
 * The phrase "if you could play this card from your hand" checks only for timing
 * restrictions and permissions. This includes both what's inherent in the card's
 * type (for example, if the card with suspend is a creature, it must be your main
 * phase and the stack must be empty) and what's imposed by other abilities, such
 * as flash or Meddling Mage's ability. Whether you could actually follow all steps
 * in playing the card is irrelevant. If the card is impossible to play due to a
 * lack of legal targets or an unpayable mana cost, for example, it may still be
 * removed from the game with suspend.
 *
 * Removing a card from the game with its suspend ability is not playing that card.
 * This action doesn't use the stack and can't be responded to.
 *
 * If a spell with suspend has targets, the targets are chosen when the spell is
 * played, not when it's removed from the game.
 *
 * If the first triggered ability of suspend is countered, no time counter is removed.
 * The ability will trigger again during its owner's next upkeep.
 *
 * When the last time counter is removed from a suspended card, the second triggered
 * ability of suspend will trigger. It doesn't matter why the time counter was removed
 * or whose effect removed it. (The _Time Spiral_ reminder text is misleading on this point.)
 *
 * If the second triggered ability of suspend is countered, the card can't be played.
 * It remains in the removed-from-the-game zone without any time counters on it for the
 * rest of the game, and it's no longer considered suspended.
 *
 * If the second triggered ability of suspend resolves, the card's owner must play the
 * spell if possible, even if that player doesn't want to. Normal timing considerations
 * for the spell are ignored (for example, if the suspended card is a creature and this
 * ability resolves during your upkeep, you’re able to play the card), but other play
 * restrictions are not ignored.
 *
 * If the second triggered ability of suspend resolves and the suspended card can't be
 * played due to a lack of legal targets or a play restriction, for example, it remains
 * in the removed-from-the-game zone without any time counters on it for the rest of the
 * game, and it's no longer considered suspended.
 *
 * As the second triggered ability of suspend resolves, if playing the suspended card
 * involves an additional cost, the card's owner must pay that cost if able. If he or
 * she can't, the card remains removed from the game. If the additional cost includes
 * mana, the situation is more complex. If the player has enough mana in his or her mana
 * pool to pay the cost, that player must do so. If the player can't possibly pay the
 * cost, the card remains removed from the game. However, if the player has the means
 * to produce enough mana to pay the cost, then he or she has a choice: The player may
 * play the spell, produce mana, and pay the cost. Or the player may choose to play no
 * mana abilities, thus making the card impossible to play because the additional mana
 * can't be paid.
 *
 * A creature played via suspend comes into play with haste. It still has haste after
 * the first turn it's in play as long as the same player controls it. As soon as another
 * player takes control of it, it loses haste.
 *
 *
 * @author LevelX2
 *
 */


public class SuspendAbility extends  ActivatedAbilityImpl<SuspendAbility> {

    private String ruleText;

    public SuspendAbility(int suspend, ManaCost cost, Card card) {
        super(Zone.HAND, new SuspendExileEffect(suspend), cost);
        this.usesStack = false;
        ruleText = new StringBuilder("Suspend ").append(suspend).append(" - ").append(cost.getText())
                                    .append(" <i>(Rather than cast this card from your hand, pay ")
                                    .append(cost.getText())
                                    .append(" and exile it with ")
                                    .append(suspend == 1 ? "a time counter":suspend + " time counters")
                                    .append(" on it.")
                                    .append(" At the beginning of your upkeep, remove a time counter. When the last is removed, cast it without paying its mana cost.")
                                    .append(card.getCardType().contains(CardType.CREATURE)? " If you play it this way and it's a creature, it gains haste until you lose control of it.":"")
                                    .append(")</i>")
                                    .toString();
        Ability ability = new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.EXILED, new RemoveCounterSourceEffect(CounterType.TIME.createInstance()), TargetController.YOU, false),
                SuspendedCondition.getInstance(),
                "At the beginning of your upkeep, if this card is suspended, remove a time counter from it.");
        ability.setRuleVisible(false);
        card.addAbility(ability);
        card.addAbility(new SuspendPlayCardAbility(card.getCardType().contains(CardType.CREATURE)));
        if (card.getCardType().contains(CardType.CREATURE)) {
            ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainHasteEffect(Duration.WhileOnBattlefield));
            ability.setRuleVisible(false);
            card.addAbility(ability);
        }
    }

    public SuspendAbility(SuspendAbility ability) {
        super(ability);
        this.ruleText = ability.getRule();
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        MageObject object = game.getObject(sourceId);
        if ((object.getCardType().contains(CardType.INSTANT) ||
                object.getAbilities().containsKey(FlashAbility.getInstance().getId()) ||
                game.getContinuousEffects().asThough(sourceId, AsThoughEffectType.CAST, game) ||
                game.canPlaySorcery(playerId))) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return ruleText;
    }

    @Override
    public SuspendAbility copy() {
        return new SuspendAbility(this);
    }

}

class SuspendExileEffect extends OneShotEffect<SuspendExileEffect> {
    private int suspend;

    public SuspendExileEffect(int suspend) {
        super(Outcome.PutCardInPlay);
        this.staticText = new StringBuilder("Suspend ").append(suspend).toString();
        this.suspend = suspend;
    }

    public SuspendExileEffect(final SuspendExileEffect effect) {
        super(effect);
        this.suspend = effect.suspend;
    }

    @Override
    public SuspendExileEffect copy() {
        return new SuspendExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            // check if user really wants to suspend the card (only if spell can't be casted because else the choose ability dialog appears)
            if (!card.getSpellAbility().canActivate(source.getControllerId(), game)) {
                if (!controller.chooseUse(outcome, new StringBuilder("Suspend ").append(card.getName()).append("?").toString(), game)) {
                    return false;
                }
            }
            UUID exileId = (UUID) game.getState().getValue("SuspendExileId" + source.getControllerId().toString());
            if (exileId == null) {
                exileId = UUID.randomUUID();
                game.getState().setValue("SuspendExileId" + source.getControllerId().toString(), exileId);
            }
            if (card.moveToExile(exileId, new StringBuilder("Suspended cards of ").append(controller.getName()).toString() , source.getSourceId(), game)) {
                card.addCounters(CounterType.TIME.createInstance(suspend), game);
                game.informPlayers(new StringBuilder(controller.getName()).append(" suspends (").append(suspend).append(") ").append(card.getName()).toString());
                return true;
            }
        }
        return false;
    }
}

class SuspendPlayCardAbility extends TriggeredAbilityImpl<SuspendPlayCardAbility> {

    public SuspendPlayCardAbility(boolean isCreature) {
        super(Zone.EXILED, new SuspendPlayCardEffect(isCreature));
        setRuleVisible(false);
    }

    public SuspendPlayCardAbility(SuspendPlayCardAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_REMOVED && event.getTargetId().equals(getSourceId())) {
            Card card = game.getCard(getSourceId());
            if (game.getState().getZone(card.getId()) == Zone.EXILED 
                    && card != null && card.getCounters().getCount(CounterType.TIME) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When the last time counter is removed from this card, if it's removed from the game, " + super.getRule();
    }

    @Override
    public SuspendPlayCardAbility copy() {
        return new SuspendPlayCardAbility(this);
    }
}

class SuspendPlayCardEffect extends OneShotEffect<SuspendPlayCardEffect> {

    public SuspendPlayCardEffect(boolean isCreature) {
        super(Outcome.PutCardInPlay);
        this.staticText = new StringBuilder("play it without paying its mana cost if able. If you can't, it remains removed from the game")
                       .append(isCreature ? ". If you play it this way and it's a creature, it gains haste until you lose control of it":"").toString();
    }

    public SuspendPlayCardEffect(final SuspendPlayCardEffect effect) {
        super(effect);
    }

    @Override
    public SuspendPlayCardEffect copy() {
        return new SuspendPlayCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player != null && card != null) {
            player.cast(card.getSpellAbility(), game, true);
        }
        return false;
    }
}

class GainHasteEffect extends ContinuousEffectImpl<GainHasteEffect> {

    private UUID suspendController;

    public GainHasteEffect(Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "If you play it this way and it's a creature, it gains haste until you lose control of it";
    }

    public GainHasteEffect(final GainHasteEffect effect) {
        super(effect);
        this.suspendController = effect.suspendController;
    }

    @Override
    public GainHasteEffect copy() {
        return new GainHasteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (suspendController == null) {
            suspendController = source.getControllerId();
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (suspendController.equals(source.getControllerId()) && !used) { // used stores if the control changed
                permanent.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
                return true;
            } else {
                used = true;
            }
        }
        return false;
    }

}
