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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.command.emblems.ArlinnEmbracedByTheMoonEmblem;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author fireshoes
 */
public class ArlinnEmbracedByTheMoon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    public ArlinnEmbracedByTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARLINN);
        this.color.setRed(true);
        this.color.setGreen(true);

        this.nightCard = true;
        this.transformable = true;

        // +1: Creatures you control get +1/+1 and gain trample until end of turn.
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn, filter);
        effect.setText("Creatures you control get +1/+1");
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("and gain trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -1: Arlinn, Embraced by the Moon deals 3 damage to target creature or player. Transform Arlinn, Embraced by the Moon.
        this.addAbility(new TransformAbility());
        ability = new LoyaltyAbility(new DamageTargetEffect(3), -1);
        ability.addTarget(new TargetCreatureOrPlayer());
        ability.addEffect(new TransformSourceEffect(false));
        this.addAbility(ability);

        // -6: You get an emblem with "Creatures you control have haste and '{T}: This creature deals damage equal to its power to target creature or player.'"
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ArlinnEmbracedByTheMoonEmblem()), -6));
    }

    public ArlinnEmbracedByTheMoon(final ArlinnEmbracedByTheMoon card) {
        super(card);
    }

    @Override
    public ArlinnEmbracedByTheMoon copy() {
        return new ArlinnEmbracedByTheMoon(this);
    }
}
