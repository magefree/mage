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

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class TibaltTheFiendBlooded extends CardImpl<TibaltTheFiendBlooded> {

    public TibaltTheFiendBlooded(UUID ownerId) {
        super(ownerId, 161, "Tibalt, the Fiend-Blooded", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{R}{R}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Tibalt");

        this.color.setRed(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(2)), false));

        // +1: Draw a card, then discard a card at random.
        this.addAbility(new LoyaltyAbility(new TibaltTheFiendBloodedFirstEffect(), 1));
        // -4: Tibalt, the Fiend-Blooded deals damage equal to the number of cards in target player's hand to that player.
        LoyaltyAbility ability = new LoyaltyAbility(new DamageTargetEffect(new CardsInTargetHandCount()), -4);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // -6: Gain control of all creatures until end of turn. Untap them. They gain haste until end of turn.
        this.addAbility(new LoyaltyAbility(new TibaltTheFiendBloodedThirdEffect(), -6));
    }

    public TibaltTheFiendBlooded(final TibaltTheFiendBlooded card) {
        super(card);
    }

    @Override
    public TibaltTheFiendBlooded copy() {
        return new TibaltTheFiendBlooded(this);
    }
}

class TibaltTheFiendBloodedFirstEffect extends OneShotEffect<TibaltTheFiendBloodedFirstEffect> {

    public TibaltTheFiendBloodedFirstEffect() {
        super(Outcome.Benefit);
        this.staticText = "Draw a card, then discard a card at random";
    }

    public TibaltTheFiendBloodedFirstEffect(final TibaltTheFiendBloodedFirstEffect effect) {
        super(effect);
    }

    @Override
    public TibaltTheFiendBloodedFirstEffect copy() {
        return new TibaltTheFiendBloodedFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, game);
            Card card = player.getHand().getRandom(game);
            if (card != null) {
                player.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}

class TibaltTheFiendBloodedThirdEffect extends OneShotEffect<TibaltTheFiendBloodedThirdEffect> {

    public TibaltTheFiendBloodedThirdEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of all creatures until end of turn. Untap them. They gain haste until end of turn";
    }

    public TibaltTheFiendBloodedThirdEffect(final TibaltTheFiendBloodedThirdEffect effect) {
        super(effect);
    }

    @Override
    public TibaltTheFiendBloodedThirdEffect copy() {
        return new TibaltTheFiendBloodedThirdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), source.getId(), game);
        for (Permanent permanent : permanents) {
            permanent.untap(game);

            ContinuousEffect effect = new TibaltTheFiendBloodedControlEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(permanent.getId()));
            game.addEffect(effect, source);

            effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent.getId()));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class TibaltTheFiendBloodedControlEffect extends ContinuousEffectImpl<TibaltTheFiendBloodedControlEffect> {

    private UUID controllerId;

    public TibaltTheFiendBloodedControlEffect(UUID controllerId) {
        super(Duration.EndOfTurn, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public TibaltTheFiendBloodedControlEffect(final TibaltTheFiendBloodedControlEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public TibaltTheFiendBloodedControlEffect copy() {
        return new TibaltTheFiendBloodedControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && controllerId != null) {
            return permanent.changeControllerId(controllerId, game);
        }
        return false;
    }
}

class CardsInTargetHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (sourceAbility != null) {
            Player player = game.getPlayer(sourceAbility.getFirstTarget());
            if (player != null) {
                return player.getHand().size();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInTargetHandCount();
    }

    @Override
    public String getMessage() {
        return "cards in that player's hand";
    }

    @Override
    public String toString() {
        return "";
    }
}
