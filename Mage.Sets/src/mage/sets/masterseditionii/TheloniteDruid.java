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
package mage.sets.masterseditionii;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class TheloniteDruid extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Forests you control");

    static {
        filter.add(new SubtypePredicate("Forest"));
    }

    public TheloniteDruid(UUID ownerId) {
        super(ownerId, 182, "Thelonite Druid", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ME2";
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.subtype.add("Druid");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, {tap}, Sacrifice a creature: Forests you control become 2/3 creatures until end of turn. They're still lands.
        ContinuousEffect effect = new BecomesCreatureAllEffect(new TheloniteDruidLandToken(), "Forests", filter, Duration.EndOfTurn);
        effect.getDependencyTypes().add(DependencyType.BecomeForest);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                effect,
                new ManaCostsImpl("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        this.addAbility(ability);
    }

    public TheloniteDruid(final TheloniteDruid card) {
        super(card);
    }

    @Override
    public TheloniteDruid copy() {
        return new TheloniteDruid(this);
    }
}

class TheloniteDruidLandToken extends Token {

    public TheloniteDruidLandToken() {
        super("", "2/3 creatures");
        cardType.add(CardType.CREATURE);
        power = new MageInt(2);
        toughness = new MageInt(3);
    }
}
