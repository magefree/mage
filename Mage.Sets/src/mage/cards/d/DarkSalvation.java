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
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class DarkSalvation extends CardImpl {

    public DarkSalvation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{X}{B}");

        // Target player creates X 2/2 black Zombie creature tokens, then up to one target creature gets -1/-1 until end of turn for each Zombie that player controls.
        this.getSpellAbility().addTarget(new TargetPlayer());
        Effect effect = new CreateTokenTargetEffect(new ZombieToken(), new ManacostVariableValue());
        effect.setText("Target player creates X 2/2 black Zombie creature tokens");
        this.getSpellAbility().addEffect(effect);
        DynamicValue value = new ZombiesControlledByTargetPlayerCount();

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, new FilterCreaturePermanent(), false));
        effect = new BoostTargetEffect(value, value, Duration.EndOfTurn, true);
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText(", then up to one target creature gets -1/-1 until end of turn for each Zombie that player controls");
        this.getSpellAbility().addEffect(effect);
    }

    public DarkSalvation(final DarkSalvation card) {
        super(card);
    }

    @Override
    public DarkSalvation copy() {
        return new DarkSalvation(this);
    }
}

class ZombiesControlledByTargetPlayerCount implements DynamicValue {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombies");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    @Override
    public ZombiesControlledByTargetPlayerCount copy() {
        return new ZombiesControlledByTargetPlayerCount();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getTargets().get(0).getFirstTarget());
        if (player != null) {
            int value = game.getBattlefield().countAll(filter, player.getId(), game);
            return -1 * value;
        } else {
            return 0;
        }
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " that player controls";
    }
}
