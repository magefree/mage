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
package mage.sets.elvesvsgoblins;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class AmbushCommander extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an Elf");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("Forests you control");

    static {
        filter.add(new SubtypePredicate("Elf"));
        filter2.add(new SubtypePredicate("Forest"));
    }

    public AmbushCommander(UUID ownerId) {
        super(ownerId, 1, "Ambush Commander", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "EVG";
        this.subtype.add("Elf");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Forests you control are 1/1 green Elf creatures that are still lands.
        ContinuousEffect effect = new BecomesCreatureAllEffect(new AmbushCommanderToken(), "lands", filter2, Duration.WhileOnBattlefield);
        effect.getDependencyTypes().add(DependencyType.BecomeForest);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // {1}{G}, Sacrifice an Elf: Target creature gets +3/+3 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(3, 3, Duration.EndOfTurn),
                new ManaCostsImpl("{1}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public AmbushCommander(final AmbushCommander card) {
        super(card);
    }

    @Override
    public AmbushCommander copy() {
        return new AmbushCommander(this);
    }
}

class AmbushCommanderToken extends Token {

    public AmbushCommanderToken() {
        super("Elf", "1/1 green Elf creatures");
        subtype.add("Elf");
        cardType.add(CardType.CREATURE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        color.setGreen(true);
    }

}
