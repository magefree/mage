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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.WarriorToken;
import mage.players.Player;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author TheElk801
 */
public final class RegnaTheRedeemer extends CardImpl {

    public RegnaTheRedeemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Partner with Krav, the Unredeemed (When this creature enters the battlefield, target player may put Krav into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Krav, the Unredeemed", true));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if your team gained life this turn, create two 1/1 white Warrior creature tokens.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new CreateTokenEffect(new WarriorToken(), 2),
                        TargetController.ANY,
                        false
                ),
                new RegnaTheRedeemerCondition(),
                "At the beginning of each end step, "
                + "if your team gained life this turn, "
                + "create two 1/1 white Warrior creature tokens"
        ), new PlayerGainedLifeWatcher());
    }

    public RegnaTheRedeemer(final RegnaTheRedeemer card) {
        super(card);
    }

    @Override
    public RegnaTheRedeemer copy() {
        return new RegnaTheRedeemer(this);
    }
}

class RegnaTheRedeemerCondition extends IntCompareCondition {

    public RegnaTheRedeemerCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        int gainedLife = 0;
        PlayerGainedLifeWatcher watcher = (PlayerGainedLifeWatcher) game.getState().getWatchers().get(PlayerGainedLifeWatcher.class.getSimpleName());
        if (watcher != null) {
            for (UUID playerId : game.getPlayerList()) {
                Player player = game.getPlayer(playerId);
                if (!player.hasOpponent(source.getControllerId(), game)) {
                    gainedLife = watcher.getLiveGained(playerId);
                    if (gainedLife > 0) {
                        break;
                    }
                }
            }
        }
        return gainedLife;
    }
}
