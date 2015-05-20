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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class OverseerOfTheDamned extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public OverseerOfTheDamned(UUID ownerId) {
        super(ownerId, 28, "Overseer of the Damned", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.expansionSetCode = "C14";
        this.subtype.add("Demon");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Overseer of the Damned enters the battlefield, you may destroy target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Whenever a nontoken creature an opponent controls dies, put a 2/2 black Zombie creature token onto the battlefield tapped.
        Token zombie = new ZombieToken("C14");
        zombie.setTokenType(2);
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(zombie, 1, true, false), false, filter));

    }

    public OverseerOfTheDamned(final OverseerOfTheDamned card) {
        super(card);
    }

    @Override
    public OverseerOfTheDamned copy() {
        return new OverseerOfTheDamned(this);
    }
}
