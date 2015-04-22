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
package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.RevealAndShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 *
 * @author North
 */
public class Progenitus extends CardImpl {

    public Progenitus(UUID ownerId) {
        super(ownerId, 121, "Progenitus", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{W}{W}{U}{U}{B}{B}{R}{R}{G}{G}");
        this.expansionSetCode = "CON";
        this.supertype.add("Legendary");
        this.subtype.add("Hydra");
        this.subtype.add("Avatar");

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        //     2/1/2009: "Protection from everything" means the following: Progenitus can't be blocked,
        //               Progenitus can't be enchanted or equipped, Progenitus can't be the target of
        //               spells or abilities, and all damage that would be dealt to Progenitus is prevented.
        //     2/1/2009: Progenitus can still be affected by effects that don't target it or deal damage
        //               to it (such as Day of Judgment).
        
        // Protection from everything
        this.addAbility(new ProgenitusProtectionAbility());
        // If Progenitus would be put into a graveyard from anywhere, reveal Progenitus and shuffle it into its owner's library instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new RevealAndShuffleIntoLibrarySourceEffect()));
    }

    public Progenitus(final Progenitus card) {
        super(card);
    }

    @Override
    public Progenitus copy() {
        return new Progenitus(this);
    }
}

class ProgenitusProtectionAbility extends ProtectionAbility {

    public ProgenitusProtectionAbility() {
        super(new FilterCard("everything"));
    }

    public ProgenitusProtectionAbility(final ProgenitusProtectionAbility ability) {
        super(ability);
    }

    @Override
    public ProgenitusProtectionAbility copy() {
        return new ProgenitusProtectionAbility(this);
    }

    @Override
    public String getRule() {
        return "Protection from everything";
    }

    @Override
    public boolean canTarget(MageObject source, Game game) {
        return false;
    }
}
