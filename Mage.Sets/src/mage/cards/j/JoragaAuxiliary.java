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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class JoragaAuxiliary extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creatures");

    static {
        filter.add(new AnotherPredicate());
    }

    public JoragaAuxiliary(UUID ownerId) {
        super(ownerId, 154, "Joraga Auxiliary", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.expansionSetCode = "OGW";
        this.subtype.add("Elf");
        this.subtype.add("Soldier");
        this.subtype.add("Ally");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {4}{G}{W}: Support 2. (Put a +1/+1 counter on each of up to two other target creatures.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SupportEffect(this, 2, true), new ManaCostsImpl("{4}{G}{W}"));
        ability.addTarget(new TargetCreaturePermanent(0, 2, filter, false));
        this.addAbility(ability);
    }

    public JoragaAuxiliary(final JoragaAuxiliary card) {
        super(card);
    }

    @Override
    public JoragaAuxiliary copy() {
        return new JoragaAuxiliary(this);
    }
}
