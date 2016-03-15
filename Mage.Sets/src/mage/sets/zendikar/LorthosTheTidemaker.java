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
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class LorthosTheTidemaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    public LorthosTheTidemaker(UUID ownerId) {
        super(ownerId, 53, "Lorthos, the Tidemaker", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{U}{U}{U}");
        this.expansionSetCode = "ZEN";
        this.supertype.add("Legendary");
        this.subtype.add("Octopus");

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Whenever Lorthos, the Tidemaker attacks, you may pay {8}. If you do, tap up to eight target permanents. Those permanents don't untap during their controllers' next untap steps.
        DoIfCostPaid effect = new DoIfCostPaid(new TapTargetEffect(), new GenericManaCost(8), "Pay {8} to tap up to 8 target permanents? (They don't untap during their controllers' next untap steps)");
        AttacksTriggeredAbility ability = new AttacksTriggeredAbility(effect, false);
        Effect effect2 = new DontUntapInControllersNextUntapStepTargetEffect();
        effect2.setText("Those permanents don't untap during their controllers' next untap steps");
        effect.addEffect(effect2);
        ability.addTarget(new TargetPermanent(0, 8, filter, false));
        this.addAbility(ability);
    }

    public LorthosTheTidemaker(final LorthosTheTidemaker card) {
        super(card);
    }

    @Override
    public LorthosTheTidemaker copy() {
        return new LorthosTheTidemaker(this);
    }
}
