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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class VengefulArchon extends CardImpl {

    public VengefulArchon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {X}: Prevent the next X damage that would be dealt to you this turn. If damage is prevented this way, Vengeful Archon deals that much damage to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VengefulArchonEffect(), new ManaCostsImpl("{X}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public VengefulArchon(final VengefulArchon card) {
        super(card);
    }

    @Override
    public VengefulArchon copy() {
        return new VengefulArchon(this);
    }

}

class VengefulArchonEffect extends PreventDamageToControllerEffect {

    public VengefulArchonEffect() {
        super(Duration.EndOfTurn, false, true, new ManacostVariableValue());
        staticText = "Prevent the next X damage that would be dealt to you this turn. If damage is prevented this way, {this} deals that much damage to target player";
    }

    public VengefulArchonEffect(final VengefulArchonEffect effect) {
        super(effect);
    }

    @Override
    public VengefulArchonEffect copy() {
        return new VengefulArchonEffect(this);
    }

    @Override
    protected PreventionEffectData preventDamageAction(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = super.preventDamageAction(event, source, game);
        int damage = preventionEffectData.getPreventedDamage();
        if (damage > 0) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(damage, source.getSourceId(), game, false, true);
            }
        }
        return preventionEffectData;
    }

}
