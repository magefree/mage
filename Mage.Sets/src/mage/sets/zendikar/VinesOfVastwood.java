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
package mage.sets.zendikar;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CantBeTargetedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterObject;
import mage.filter.FilterStackObject;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author nantuko
 */
public class VinesOfVastwood extends CardImpl {

    private static final FilterObject filter = new FilterStackObject("spells or abilities your opponents control");

    private static final String staticText = "If {this} was kicked, that creature gets +4/+4 until end of turn";

    public VinesOfVastwood(UUID ownerId) {
        super(ownerId, 193, "Vines of Vastwood", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "ZEN";

        // Kicker {G} (You may pay an additional {G} as you cast this spell.)
        this.addAbility(new KickerAbility("{G}"));

        // Target creature can't be the target of spells or abilities your opponents control this turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CantBeTargetedTargetEffect(filter, Duration.EndOfTurn, TargetController.OPPONENT));

        // If Vines of Vastwood was kicked, that creature gets +4/+4 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new BoostTargetEffect(4, 4, Duration.EndOfTurn),
                new LockedInCondition(KickedCondition.getInstance()), staticText));
    }

    public VinesOfVastwood(final VinesOfVastwood card) {
        super(card);
    }

    @Override
    public VinesOfVastwood copy() {
        return new VinesOfVastwood(this);
    }
}
