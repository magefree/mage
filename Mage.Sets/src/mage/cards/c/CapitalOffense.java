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

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardInfo;
import mage.constants.CardType;
import mage.cards.repository.CardRepository;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public class CapitalOffense extends CardImpl {

    public CapitalOffense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // target creature gets -x/-x until end of turn, where x is the number of times a capital letter appears in its rules text. (ignore reminder text and flavor text.)
        DynamicValue xValue = new NumberOfCapitalsInTextOfTargetCreatureCount();
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public CapitalOffense(final CapitalOffense card) {
        super(card);
    }

    @Override
    public CapitalOffense copy() {
        return new CapitalOffense(this);
    }
}

class NumberOfCapitalsInTextOfTargetCreatureCount implements DynamicValue {

    @Override
    public NumberOfCapitalsInTextOfTargetCreatureCount copy() {
        return new NumberOfCapitalsInTextOfTargetCreatureCount();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = game.getPermanent(sourceAbility.getTargets().get(0).getFirstTarget());
        if (permanent != null) {
            int capitals = 0;
            List<CardInfo> cards = CardRepository.instance.findCards(permanent.getName());

            if (cards != null) {
                for (CardInfo cardInfo : cards) {
                    Card dummy = cardInfo != null ? cardInfo.getCard() : null;
                    for (String line : dummy.getRules()) {
                        line = line.replaceAll("(?i)<i.*?</i>", ""); // Ignoring reminder text in italic
                        line = line.replaceAll("\\{this\\}", permanent.getName());
                        capitals += line.length() - line.replaceAll("[A-Z]", "").length();
                    }
                    return -1 * capitals;
                }
            }
        }
        return 0;
    }

    @Override
    public String getMessage() {
        return "target creature gets -x/-x until end of turn, where x is the number of times a capital letter appears in its rules text. (ignore reminder text and flavor text.)";
    }
}
