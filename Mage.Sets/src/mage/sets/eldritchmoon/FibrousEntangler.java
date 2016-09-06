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
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class FibrousEntangler extends CardImpl {

    public FibrousEntangler(UUID ownerId) {
        super(ownerId, 174, "Fibrous Entangler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "");
        this.expansionSetCode = "EMN";
        this.subtype.add("Eldrazi");
        this.subtype.add("Werewolf");
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Fibrous Entangler must be blocked if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // Fibrous Entangler can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(Duration.WhileOnBattlefield, 1)));
    }

    public FibrousEntangler(final FibrousEntangler card) {
        super(card);
    }

    @Override
    public FibrousEntangler copy() {
        return new FibrousEntangler(this);
    }
}
