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
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class Floodgate extends CardImpl {

    public Floodgate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Floodgate has flying, sacrifice it.
        this.addAbility(new FloodgateHasFlyingStateTriggeredAbility());

        // When Floodgate leaves the battlefield, it deals damage equal to half the number of Islands you control, rounded down, to each nonblue creature without flying.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new FloodgateDamageEffect(), false));
    }

    private Floodgate(final Floodgate card) {
        super(card);
    }

    @Override
    public Floodgate copy() {
        return new Floodgate(this);
    }
}

class FloodgateHasFlyingStateTriggeredAbility extends StateTriggeredAbility {

    public FloodgateHasFlyingStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    private FloodgateHasFlyingStateTriggeredAbility(final FloodgateHasFlyingStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FloodgateHasFlyingStateTriggeredAbility copy() {
        return new FloodgateHasFlyingStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null && permanent.getAbilities().contains(FlyingAbility.getInstance())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} has flying, sacrifice it.";
    }

}

class FloodgateDamageEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent();

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLUE)));
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(SubType.ISLAND.getPredicate());
    }

    public FloodgateDamageEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals damage equal to half the number of Islands you control, "
                + "rounded down, to each nonblue creature without flying";
    }

    private FloodgateDamageEffect(final FloodgateDamageEffect effect) {
        super(effect);
    }

    @Override
    public FloodgateDamageEffect copy() {
        return new FloodgateDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int islandCount = new PermanentsOnBattlefieldCount(filter2).calculate(game, source, this);
        islandCount = Math.floorDiv(islandCount, 2);
        return new DamageAllEffect(islandCount, filter).apply(game, source);
    }
}
