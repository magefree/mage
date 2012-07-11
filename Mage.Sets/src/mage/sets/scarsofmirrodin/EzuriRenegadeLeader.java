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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class EzuriRenegadeLeader extends CardImpl<EzuriRenegadeLeader> {

    private static final FilterCreaturePermanent elfFilter = new FilterCreaturePermanent("Elf creatures");
    private static final FilterControlledCreaturePermanent notEzuri = new FilterControlledCreaturePermanent();

    static {
        elfFilter.add(new SubtypePredicate("Elf"));

        notEzuri.add(new SubtypePredicate("Elf"));
        notEzuri.add(Predicates.not(new NamePredicate("Ezuri, Renegade Leader")));
    }

    public EzuriRenegadeLeader(UUID ownerId) {
        super(ownerId, 119, "Ezuri, Renegade Leader", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "SOM";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ezuriRegen = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl("{G}"));
        TargetControlledCreaturePermanent regenTarget = new TargetControlledCreaturePermanent(1, 1, notEzuri, false);
        regenTarget.setTargetName("another target Elf");
        ezuriRegen.addTarget(regenTarget);
        this.addAbility(ezuriRegen);

        Ability ezuriBoost = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(3, 3, Duration.EndOfTurn, elfFilter, false),
                new ManaCostsImpl("{2}{G}{G}{G}"));
        ezuriBoost.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, elfFilter));
        this.addAbility(ezuriBoost);
    }

    public EzuriRenegadeLeader(final EzuriRenegadeLeader card) {
        super(card);
    }

    @Override
    public EzuriRenegadeLeader copy() {
        return new EzuriRenegadeLeader(this);
    }
}
