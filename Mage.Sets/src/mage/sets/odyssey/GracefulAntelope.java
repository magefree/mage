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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.continious.BecomesBasicLandTargetEffect;
import mage.abilities.keyword.PlainswalkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author cbt33, Loki (Contaminated Ground), Plopman (Larceny)
 */
public class GracefulAntelope extends CardImpl<GracefulAntelope> {

    public GracefulAntelope(UUID ownerId) {
        super(ownerId, 24, "Graceful Antelope", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Antelope");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Plainswalk
        this.addAbility(new PlainswalkAbility());
        // Whenever Graceful Antelope deals combat damage to a player, you may have target land become a Plains until Graceful Antelope leaves the battlefield.
        //Ability ability = new SimpleTriggeredAbility(Zone.BATTLEFIELD, new BecomesBasicLandTargetEffect(Duration.WhileOnBattlefield), new TapSourceCost());
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new BecomesBasicLandTargetEffect(Duration.WhileOnBattlefield,"Plains"), true);
        Target target = new TargetLandPermanent();
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public GracefulAntelope(final GracefulAntelope card) {
        super(card);
    }

    @Override
    public GracefulAntelope copy() {
        return new GracefulAntelope(this);
    }
}
