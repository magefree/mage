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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.command.emblems.TamiyoTheMoonSageEmblem;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author North, noxx
 */
public class TamiyoTheMoonSage extends CardImpl {

    public TamiyoTheMoonSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Tamiyo");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Tap target permanent. It doesn't untap during its controller's next untap step.
        LoyaltyAbility ability = new LoyaltyAbility(new TapTargetEffect(), 1);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect());
        Target target = new TargetPermanent();
        ability.addTarget(target);
        this.addAbility(ability);

        // -2: Draw a card for each tapped creature target player controls.
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(new TappedCreaturesControlledByTargetCount()), -2);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -8: You get an emblem with "You have no maximum hand size" and "Whenever a card is put into your graveyard from anywhere, you may return it to your hand."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TamiyoTheMoonSageEmblem()), -8));
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
        filter.add(new TappedPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().countAll(filter, sourceAbility.getFirstTarget(), game);
    }

    @Override
    public DynamicValue copy() {
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

/**
 * Emblem with "You have no maximum hand size" and "Whenever a card is put into
 * your graveyard from anywhere, you may return it to your hand."
 */
