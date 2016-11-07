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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class ObiWanKenobi extends CardImpl {

    public ObiWanKenobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{3}{W}{U}");
        this.subtype.add("Obi-Wan");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1:Up to one target creature you control gains vigilance and protection from color of your choice until end of turn.
        Effect effect = new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Up to one target creature you control gains vigilance");
        Ability ability = new LoyaltyAbility(effect, +1);
        effect = new GainProtectionFromColorTargetEffect(Duration.EndOfTurn);
        effect.setText("and protection from color of your choice until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -1:Tap up to three target creatures.
        ability = new LoyaltyAbility(new TapTargetEffect(), -1);
        ability.addTarget(new TargetCreaturePermanent(0, 3));
        this.addAbility(ability);

        // -7:You get emblem with "Creatures you control get +1/+1 and have vigilance, first strike, and lifelink."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ObiWanKenobiEmblem()), -7));

    }

    public ObiWanKenobi(final ObiWanKenobi card) {
        super(card);
    }

    @Override
    public ObiWanKenobi copy() {
        return new ObiWanKenobi(this);
    }
}

class ObiWanKenobiEmblem extends Emblem {

    // Creatures you control get +1/+1 and have vigilance, first strike, and lifelink
    public ObiWanKenobiEmblem() {
        this.setName("Emblem - Obi-Wan");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new BoostControlledEffect(1, 1, Duration.EndOfGame));
        Effect effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfGame);
        effect.setText("and have vigilance,");
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield);
        effect.setText("first strike,");
        ability.addEffect(effect);
        effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield);
        effect.setText("and lifelink.");
        ability.addEffect(effect);
        getAbilities().add(ability);
    }
}
