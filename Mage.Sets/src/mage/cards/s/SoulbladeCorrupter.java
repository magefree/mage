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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class SoulbladeCorrupter extends CardImpl {

    public SoulbladeCorrupter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Soulblade Renewer (When this creature enters the battlefield, target player may put Soulblade Renewer into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Soulblade Renewer"));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature with a +1/+1 counter on it attacks one of your opponents, that creature gains deathtouch until end of turn.
        this.addAbility(new SoulbladeCorrupterTriggeredAbility());
    }

    public SoulbladeCorrupter(final SoulbladeCorrupter card) {
        super(card);
    }

    @Override
    public SoulbladeCorrupter copy() {
        return new SoulbladeCorrupter(this);
    }
}

class SoulbladeCorrupterTriggeredAbility extends AttacksAllTriggeredAbility {

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature with a +1/+1 counter on it");

    static {
        filter2.add(new CounterPredicate(CounterType.P1P1));
    }

    SoulbladeCorrupterTriggeredAbility() {
        super(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(),
                Duration.EndOfTurn
        ).setText("that creature gains deathtouch until end of turn"), false, filter2, SetTargetPointer.PERMANENT, false);
    }

    SoulbladeCorrupterTriggeredAbility(final SoulbladeCorrupterTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                Player player = game.getPlayer(permanent.getControllerId());
                if (player != null && player.hasOpponent(getControllerId(), game)) {
                    getEffects().setTargetPointer(new FixedTarget(permanent, game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature with a +1/+1 counter on it attacks one of your opponents, that creature gains deathtouch until end of turn.";
    }

    @Override
    public SoulbladeCorrupterTriggeredAbility copy() {
        return new SoulbladeCorrupterTriggeredAbility(this);
    }

}
