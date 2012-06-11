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
package mage.sets.avacynrestored;

import mage.Constants.*;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.SkipNextUntapTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continious.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author North
 */
public class TamiyoTheMoonSage extends CardImpl<TamiyoTheMoonSage> {

    public TamiyoTheMoonSage(UUID ownerId) {
        super(ownerId, 79, "Tamiyo, the Moon Sage", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Tamiyo");

        this.color.setBlue(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), ""));

        // +1: Tap target permanent. It doesn't untap during its controller's next untap step.
        LoyaltyAbility ability = new LoyaltyAbility(new TapTargetEffect(), 1);
        ability.addEffect(new SkipNextUntapTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
        // -2: Draw a card for each tapped creature target player controls.
        ability = new LoyaltyAbility(new DrawCardControllerEffect(new TappedCreaturesControlledByTargetCount()), -2);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // -8: You get an emblem with "You have no maximum hand size" and "Whenever a card is put into your graveyard from anywhere, you may return it to your hand."
        this.addAbility(new LoyaltyAbility(new TamiyoTheMoonSageThirdEffect(), -8));
    }

    public TamiyoTheMoonSage(final TamiyoTheMoonSage card) {
        super(card);
    }

    @Override
    public TamiyoTheMoonSage copy() {
        return new TamiyoTheMoonSage(this);
    }
}

class TappedCreaturesControlledByTargetCount implements DynamicValue {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.setTapped(true);
        filter.setUseTapped(true);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        return game.getBattlefield().countAll(filter, sourceAbility.getFirstTarget(), game);
    }

    @Override
    public DynamicValue clone() {
        return new TappedCreaturesControlledByTargetCount();
    }

    @Override
    public String toString() {
        return "a";
    }

    @Override
    public String getMessage() {
        return "tapped creature target player controls";
    }
}

class TamiyoTheMoonSageThirdEffect extends OneShotEffect<TamiyoTheMoonSageThirdEffect> {

    public TamiyoTheMoonSageThirdEffect() {
        super(Outcome.Benefit);
        this.staticText = "You get an emblem with \"You have no maximum hand size\" and \"Whenever a card is put into your graveyard from anywhere, you may return it to your hand.\"";
    }

    public TamiyoTheMoonSageThirdEffect(final TamiyoTheMoonSageThirdEffect effect) {
        super(effect);
    }

    @Override
    public TamiyoTheMoonSageThirdEffect copy() {
        return new TamiyoTheMoonSageThirdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // TODO: move this to emblem
        game.addEffect(new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, false), source);
        TamiyoTheMoonSageTriggeredAbility ability = new TamiyoTheMoonSageTriggeredAbility();
        ability.setControllerId(source.getControllerId());
        ability.setSourceId(source.getSourceId());
        game.getState().addAbility(ability);
        return true;
    }
}

class TamiyoTheMoonSageTriggeredAbility extends TriggeredAbilityImpl<TamiyoTheMoonSageTriggeredAbility> {

    public TamiyoTheMoonSageTriggeredAbility() {
        super(Zone.COMMAND, new TamiyoTheMoonSageEffect(), true);
    }

    public TamiyoTheMoonSageTriggeredAbility(final TamiyoTheMoonSageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TamiyoTheMoonSageTriggeredAbility copy() {
        return new TamiyoTheMoonSageTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getOwnerId().equals(this.getControllerId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(card.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a card is put into your graveyard from anywhere, you may return it to your hand.";
    }
}

class TamiyoTheMoonSageEffect extends OneShotEffect<TamiyoTheMoonSageEffect> {

    public TamiyoTheMoonSageEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return it to your hand";
    }

    public TamiyoTheMoonSageEffect(final TamiyoTheMoonSageEffect effect) {
        super(effect);
    }

    @Override
    public TamiyoTheMoonSageEffect copy() {
        return new TamiyoTheMoonSageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.targetPointer.getFirst(game, source));
        if (card != null) {
            return card.moveToZone(Zone.HAND, source.getId(), game, true);
        }
        return false;
    }
}
