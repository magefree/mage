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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public class SirenOfTheSilentSong extends CardImpl<SirenOfTheSilentSong> {

    public SirenOfTheSilentSong(UUID ownerId) {
        super(ownerId, 155, "Siren of the Silent Song", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Zombie");
        this.subtype.add("Siren");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Inspired</i> - Whenever Siren of the Silent Song becomes untapped, each opponent discards a card, then puts the top card of his or her library into his or her graveyard.
        Ability ability = new InspiredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT));
        Effect effect = new PutTopCardOfLibraryIntoGraveEachPlayerEffect(1, TargetController.OPPONENT);
        effect.setText(", then puts the top card of his or her library into his or her graveyard");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public SirenOfTheSilentSong(final SirenOfTheSilentSong card) {
        super(card);
    }

    @Override
    public SirenOfTheSilentSong copy() {
        return new SirenOfTheSilentSong(this);
    }
}
