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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 *
 */
public class DrownerInitiate extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public DrownerInitiate(UUID ownerId) {
        super(ownerId, 36, "Drowner Initiate", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player casts a blue spell, you may pay {1}. If you do, target player puts the top two cards of his or her library into his or her graveyard.
        Ability ability = new SpellCastAllTriggeredAbility(new DoIfCostPaid(new PutLibraryIntoGraveTargetEffect(2), new ManaCostsImpl("{1}")), filter, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public DrownerInitiate(final DrownerInitiate card) {
        super(card);
    }

    @Override
    public DrownerInitiate copy() {
        return new DrownerInitiate(this);
    }
}
