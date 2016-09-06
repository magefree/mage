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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class ConduitOfStorms extends CardImpl {

    public ConduitOfStorms(UUID ownerId) {
        super(ownerId, 124, "Conduit of Storms", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Werewolf");
        this.subtype.add("Horror");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.canTransform = true;
        this.secondSideCard = new ConduitOfEmrakul(ownerId);

        // Whenever Conduit of Storms attacks, add {R} to your mana pool at the beginning of your next main phase this turn.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfMainPhaseDelayedTriggeredAbility(
                        new AddManaToManaPoolSourceControllerEffect(Mana.RedMana(1)), false, TargetController.YOU, AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection.NEXT_MAIN_THIS_TURN));
        effect.setText("add {R} to your mana pool at the beginning of your next main phase this turn");
        this.addAbility(new AttacksTriggeredAbility(effect, false));
        // {3}{R}{R}: Transform Conduit of Storms.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(true), new ManaCostsImpl("{3}{R}{R}")));
    }

    public ConduitOfStorms(final ConduitOfStorms card) {
        super(card);
    }

    @Override
    public ConduitOfStorms copy() {
        return new ConduitOfStorms(this);
    }
}
