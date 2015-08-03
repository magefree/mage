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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachTurnStaticAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class FlamewakePhoenix extends CardImpl {

    public FlamewakePhoenix(UUID ownerId) {
        super(ownerId, 100, "Flamewake Phoenix", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Phoenix");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Flamewake Phoenix attacks each turn if able.
        this.addAbility(new AttacksEachTurnStaticAbility());

        // <i>Ferocious</i> - At the beginning of combat on your turn, if you control a creature with power 4 or greater, you may pay {R}. If you do, return Flamewake Phoenix from your graveyard to the battlefield.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        Zone.GRAVEYARD,
                        new DoIfCostPaid(new ReturnToBattlefieldUnderOwnerControlSourceEffect(), new ManaCostsImpl("{R")),
                        TargetController.YOU, false, false),
                FerociousCondition.getInstance(),
                "<i>Ferocious</i> &mdash; At the beginning of combat on your turn, if you control a creature with power 4 or greater, you may pay {R}. If you do, return {this} from your graveyard to the battlefield."
        ));
    }

    public FlamewakePhoenix(final FlamewakePhoenix card) {
        super(card);
    }

    @Override
    public FlamewakePhoenix copy() {
        return new FlamewakePhoenix(this);
    }
}
