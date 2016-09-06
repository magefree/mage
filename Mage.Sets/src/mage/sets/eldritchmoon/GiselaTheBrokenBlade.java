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
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public class GiselaTheBrokenBlade extends CardImpl {

    public GiselaTheBrokenBlade(UUID ownerId) {
        super(ownerId, 28, "Gisela, the Broken Blade", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "EMN";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");
        this.subtype.add("Horror");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        
        // At the beginning of your end step, if you both own and control Gisela, the Broken Blade and a creature named Bruna, the Fading Light, exile them, then meld them into Brisela, Voice of Nightmares.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new MeldEffect("Bruna, the Fading Light", new BriselaVoiceOfNightmares(ownerId)), TargetController.YOU, false),
                new MeldCondition("Bruna, the Fading Light"),
                "At the beginning of your end step, if you both own and control {this} and a creature named Bruna, the Fading Light, exile them, "
                        + "then meld them into Brisela, Voice of Nightmares."));
    }

    public GiselaTheBrokenBlade(final GiselaTheBrokenBlade card) {
        super(card);
    }

    @Override
    public GiselaTheBrokenBlade copy() {
        return new GiselaTheBrokenBlade(this);
    }
}
