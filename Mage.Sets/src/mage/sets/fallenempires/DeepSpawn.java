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
package mage.sets.fallenempires;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutTopCardOfYourLibraryToGraveyardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public class DeepSpawn extends CardImpl {

    public DeepSpawn(UUID ownerId) {
        super(ownerId, 34, "Deep Spawn", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{U}{U}{U}");
        this.expansionSetCode = "FEM";
        this.subtype.add("Homarid");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice Deep Spawn unless you put the top two cards of your library into your graveyard.
        Effect effect = new SacrificeSourceUnlessPaysEffect(new PutTopCardOfYourLibraryToGraveyardCost(2));
        effect.setText("sacrifice {this} unless you put the top two cards of your library into your graveyard");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false));
        // {U}: Deep Spawn gains shroud until end of turn and doesn't untap during your next untap step. Tap Deep Spawn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(
            ShroudAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{U}"));
        effect = new DontUntapInControllersNextUntapStepSourceEffect();
        effect.setText("and doesn't untap during your next untap step");
        ability.addEffect(effect);
        ability.addEffect(new TapSourceEffect());
        this.addAbility(ability);
    }

    public DeepSpawn(final DeepSpawn card) {
        super(card);
    }

    @Override
    public DeepSpawn copy() {
        return new DeepSpawn(this);
    }
}
