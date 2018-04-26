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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public class TrialOfSolidarity extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Cartouche");

    static {
        filter.add(new SubtypePredicate(SubType.CARTOUCHE));
    }

    public TrialOfSolidarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Trial of Solidarity enters the battlefield, creatures you control get +2/+1 and gain vigilance until end of turn.
        Effect effect = new BoostControlledEffect(2, 1, Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("creatures you control get +2/+1");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText(" and gain vigilance until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // When a Cartouche enters the battlefield under you control, return Trial of Solidarity to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter,
                "When a Cartouche enters the battlefield under your control, return {this} to its owner's hand"));
    }

    public TrialOfSolidarity(final TrialOfSolidarity card) {
        super(card);
    }

    @Override
    public TrialOfSolidarity copy() {
        return new TrialOfSolidarity(this);
    }
}
