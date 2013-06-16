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
package mage.sets.dragonsmaze;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BecomesCreatureAllEffect;
import mage.abilities.effects.common.continious.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continious.LoseAllAbilitiesAllEffect;
import mage.abilities.effects.common.continious.LoseAllAbilitiesTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Dragonshift extends CardImpl<Dragonshift> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("all creatures you controls");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public Dragonshift(UUID ownerId) {
        super(ownerId, 66, "Dragonshift", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");
        this.expansionSetCode = "DGM";

        this.color.setRed(true);
        this.color.setBlue(true);

        // Until end of turn, target creature you control becomes a 4/4 blue and red Dragon, loses all abilities, and gains flying.
        this.getSpellAbility().addEffect(new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new DragonToken(), null, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(true));

        // Overload {3}{U}{U}{R}{R}
        Ability ability = new OverloadAbility(this, new LoseAllAbilitiesAllEffect(new FilterControlledCreaturePermanent(""), Duration.EndOfTurn), new ManaCostsImpl("{3}{U}{U}{R}{R}"));
        ability.addEffect(new BecomesCreatureAllEffect(new DragonToken(), null, filter, Duration.EndOfTurn));
        this.addAbility(ability);
    }

    public Dragonshift(final Dragonshift card) {
        super(card);
    }

    @Override
    public Dragonshift copy() {
        return new Dragonshift(this);
    }

    private class DragonToken extends Token {

        public DragonToken() {
            super("Dragon", "4/4 blue and red Dragon with flying");
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            color.setRed(true);
            subtype.add("Dragon");
            power = new MageInt(4);
            toughness = new MageInt(4);
            this.addAbility(FlyingAbility.getInstance());
        }

    }
}
