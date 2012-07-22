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

import mage.Constants;
import mage.MageInt;
import mage.abilities.common.DiesAndDealtDamageThisTurnTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 * @author LevelX
 */
public class BushiTenderfoot extends CardImpl<BushiTenderfoot> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.add(new WasDealtDamageThisTurnPredicate());
    }

    public BushiTenderfoot(UUID ownerId) {
        super(ownerId, 2, "Bushi Tenderfoot", Constants.Rarity.UNCOMMON, new Constants.CardType[]{Constants.CardType.CREATURE}, "{W}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;

        // When that creature is put into a graveyard this turn, flip Initiate of Blood.
        this.addAbility(new DiesAndDealtDamageThisTurnTriggeredAbility(new FlipSourceEffect()));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new KenzoTheHardhearted()), FlippedCondition.getInstance(), "")));


    }

    public BushiTenderfoot(final BushiTenderfoot card) {
        super(card);
    }

    @Override
    public BushiTenderfoot copy() {
        return new BushiTenderfoot(this);
    }
}

class KenzoTheHardhearted extends Token {

    KenzoTheHardhearted() {
        super("Kenzo the Hardhearted", "");
        supertype.add("Legendary");
        cardType.add(Constants.CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Human");
        subtype.add("Samurai");
        power = new MageInt(3);
        toughness = new MageInt(4);

        // Double strike; bushido 2 (When this blocks or becomes blocked, it gets +2/+2 until end of turn.)
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(new BushidoAbility(2));
    }
}
