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
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.command.Emblem;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author JRHerlehy
 */
public class TezzeretTheSchemer extends CardImpl {

    public TezzeretTheSchemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{B}");
        this.subtype.add("Tezzeret");

        //Starting Loyalty - 5
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Create a colorless artifact token named Etherium Cell which has "{T}, Sacrifice this artifact: Add one mana of any color to your mana pool."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new EtheriumCellToken()), 1));

        // -2: Target creature gets +X/-X until end of turn, where X is the number of artifacts you control.
        DynamicValue count = new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent("artifacts you control"));
        Effect effect = new BoostTargetEffect(count, new SignInversionDynamicValue(count), Duration.EndOfTurn, true);
        effect.setText("Target creature gets +X/-X until end of turn, where X is the number of artifacts you control");
        Ability ability = new LoyaltyAbility(effect, -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -7: You get an emblem with "At the beginning of combat on your turn, target artifact you control becomes an artifact creature with base power and toughness 5/5."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TezzeretTheSchemerEmblem()), -7));
    }

    public TezzeretTheSchemer(final TezzeretTheSchemer card) {
        super(card);
    }

    @Override
    public TezzeretTheSchemer copy() {
        return new TezzeretTheSchemer(this);
    }
}

class TezzeretTheSchemerEmblem extends Emblem {

    public TezzeretTheSchemerEmblem() {
        this.setName("Emblem Tezzeret");

        Effect effect = new AddCardTypeTargetEffect(CardType.CREATURE, Duration.EndOfGame);
        effect.setText("target artifact you control becomes an artifact creature");
        Ability ability = new BeginningOfCombatTriggeredAbility(Zone.COMMAND, effect, TargetController.YOU, false, true);
        effect = new SetPowerToughnessTargetEffect(5, 5, Duration.EndOfGame);
        effect.setText("with base power and toughness 5/5");
        ability.addEffect(effect);
        ability.addTarget(new TargetPermanent(new FilterControlledArtifactPermanent()));
        this.getAbilities().add(ability);
    }
}

class EtheriumCellToken extends Token {

    public EtheriumCellToken() {
        super("Etherium Cell", "colorless artifact token named Etherium Cell which has \"{T}, Sacrifice this artifact: Add one mana of any color to your mana pool.\"");
        this.setOriginalExpansionSetCode("AER");
        cardType.add(CardType.ARTIFACT);

        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());

        this.addAbility(ability);
    }
}
