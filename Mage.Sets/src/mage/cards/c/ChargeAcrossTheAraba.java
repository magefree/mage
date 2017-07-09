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
package mage.cards.c;

    import mage.abilities.dynamicvalue.DynamicValue;
    import mage.abilities.dynamicvalue.common.SweepNumber;
    import mage.abilities.effects.common.continuous.BoostControlledEffect;
    import mage.abilities.effects.keyword.SweepEffect;
    import mage.cards.CardImpl;
    import mage.cards.CardSetInfo;
    import mage.constants.CardType;
    import mage.constants.Duration;
    import mage.constants.SubType;

    import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ChargeAcrossTheAraba extends CardImpl {

    public ChargeAcrossTheAraba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{W}");
        this.subtype.add("Arcane");


        // Sweep - Return any number of Plains you control to their owner's hand. Creatures you control get +1/+1 until end of turn for each Plains returned this way.
        this.getSpellAbility().addEffect(new SweepEffect(SubType.PLAINS));
        DynamicValue sweepValue = new SweepNumber("Plains", true);
        this.getSpellAbility().addEffect(new BoostControlledEffect(sweepValue, sweepValue, Duration.EndOfTurn));

    }

    public ChargeAcrossTheAraba(final ChargeAcrossTheAraba card) {
        super(card);
    }

    @Override
    public ChargeAcrossTheAraba copy() {
        return new ChargeAcrossTheAraba(this);
    }
}
