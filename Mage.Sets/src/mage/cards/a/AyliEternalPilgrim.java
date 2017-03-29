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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class AyliEternalPilgrim extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("another creature");
    static {
        filter.add(new AnotherPredicate());
    }

    public AyliEternalPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Kor");
        this.subtype.add("Cleric");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        
        // {1}, Sacrifice another creature: You gain life equal to the sacrificed creature's toughness.
        Effect effect = new GainLifeEffect(new SacrificeCostCreaturesToughness());
        effect.setText("You gain life equal to the sacrificed creature's toughness");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
        
        // {1}{W}{B}, Sacrifice another creature: Exile target nonland permanent. Activate this ability only if you have at least 10 life more than your starting life total.
        ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileTargetEffect(),
                new ManaCostsImpl("{1}{W}{B}"),
                new AyliEternalPilgrimCondition(),
                "{1}{W}{B}, Sacrifice another creature: Exile target nonland permanent. Activate this ability only if you have at least 10 life more than your starting life total");
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    public AyliEternalPilgrim(final AyliEternalPilgrim card) {
        super(card);
    }

    @Override
    public AyliEternalPilgrim copy() {
        return new AyliEternalPilgrim(this);
    }
}

class AyliEternalPilgrimCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player.getLife() >= game.getLife() + 10;
    }
}
