/*
 * 
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 *
 */
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAndDealtDamageThisTurnTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX
 */
public class InitiateOfBlood extends CardImpl<InitiateOfBlood> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.setUseDamageDealt(true);
        filter.setDamageDealt(true);
    }

    public InitiateOfBlood(UUID ownerId) {
        super(ownerId, 173, "Initiate of Blood", Constants.Rarity.UNCOMMON, new Constants.CardType[]{Constants.CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Ogre");
        this.subtype.add("Shaman");
        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Initiate of Blood deals 1 damage to target creature that was dealt damage this turn. 
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // When that creature is put into a graveyard this turn, flip Initiate of Blood.
        this.addAbility(new DiesAndDealtDamageThisTurnTriggeredAbility(new FlipSourceEffect()));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new GokaTheUnjust()), FlippedCondition.getInstance(), "")));


    }

    public InitiateOfBlood(final InitiateOfBlood card) {
        super(card);
    }

    @Override
    public InitiateOfBlood copy() {
        return new InitiateOfBlood(this);
    }
}

class GokaTheUnjust extends Token {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.setUseDamageDealt(true);
        filter.setDamageDealt(true);
    }

    GokaTheUnjust() {
        super("Goka the Unjust", "");
        supertype.add("Legendary");
        cardType.add(Constants.CardType.CREATURE);
        color.setRed(true);
        subtype.add("Ogre");
        subtype.add("Shaman");
        power = new MageInt(4);
        toughness = new MageInt(4);

        // {T}: Goka the Unjust deals 4 damage to target creature that was dealt damage this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }
}
