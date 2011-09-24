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
package mage.sets.innistrad;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class EliteInquisitor extends CardImpl<EliteInquisitor> {

    private static final FilterPermanent filter1 = new FilterCreaturePermanent("Vampires");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("Werewolves");
    private static final FilterPermanent filter3 = new FilterCreaturePermanent("Zombies");

    static {
        filter1.getSubtype().add("Vampire");
        filter2.getSubtype().add("Werewolf");
        filter3.getSubtype().add("Zombie");
        filter1.setScopeSubtype(Filter.ComparisonScope.Any);
        filter2.setScopeSubtype(Filter.ComparisonScope.Any);
        filter3.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public EliteInquisitor(UUID ownerId) {
        super(ownerId, 13, "Elite Inquisitor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());

        // Protection from Vampires, from Werewolves, and from Zombies.
        this.addAbility(new ProtectionAbility(filter1));
        this.addAbility(new ProtectionAbility(filter2));
        this.addAbility(new ProtectionAbility(filter3));
    }

    public EliteInquisitor(final EliteInquisitor card) {
        super(card);
    }

    @Override
    public EliteInquisitor copy() {
        return new EliteInquisitor(this);
    }
}
