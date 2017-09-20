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
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TroveOfTemptation extends CardImpl {

    public TroveOfTemptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Each opponent must attack you or a planeswalker you control with at least one creature each combat if able.
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TroveOfTemptationForceAttackEffect(Duration.WhileOnBattlefield)));

        // At the beginning of your end step, create a colorless Treasure artifact token with "{t}, Sacrifice this artifact: Add one mana of any color to your mana pool.”
        addAbility(new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false));
    }

    public TroveOfTemptation(final TroveOfTemptation card) {
        super(card);
    }

    @Override
    public TroveOfTemptation copy() {
        return new TroveOfTemptation(this);
    }
}

class TroveOfTemptationForceAttackEffect extends RequirementEffect {

    public TroveOfTemptationForceAttackEffect(Duration duration) {
        super(duration, true);
        staticText = "Each opponent must attack you or a planeswalker you control with at least one creature each combat if able";
    }

    public TroveOfTemptationForceAttackEffect(final TroveOfTemptationForceAttackEffect effect) {
        super(effect);
    }

    @Override
    public TroveOfTemptationForceAttackEffect copy() {
        return new TroveOfTemptationForceAttackEffect(this);
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.hasOpponent(game.getActivePlayerId(), game);
    }

    @Override
    public UUID playerMustBeAttackedIfAble(Ability source, Game game) {
        return source.getControllerId();
    }

}
