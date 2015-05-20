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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class DireUndercurrents extends CardImpl {

    private final static String rule1 = "Whenever a blue creature enters the battlefield under your control, you may have target player draw a card.";
    private final static String rule2 = "Whenever a black creature enters the battlefield under your control, you may have target player discard a card.";

    private final static FilterControlledPermanent filterBlue = new FilterControlledCreaturePermanent();
    private final static FilterControlledPermanent filterBlack = new FilterControlledCreaturePermanent();

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public DireUndercurrents(UUID ownerId) {
        super(ownerId, 159, "Dire Undercurrents", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{U/B}{U/B}");
        this.expansionSetCode = "SHM";


        // Whenever a blue creature enters the battlefield under your control, you may have target player draw a card.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(1), filterBlue, true, rule1);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever a black creature enters the battlefield under your control, you may have target player discard a card.
        Ability ability2 = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), filterBlack, true, rule2);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);

    }

    public DireUndercurrents(final DireUndercurrents card) {
        super(card);
    }

    @Override
    public DireUndercurrents copy() {
        return new DireUndercurrents(this);
    }
}
