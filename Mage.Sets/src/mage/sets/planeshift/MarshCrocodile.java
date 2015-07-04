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
package mage.sets.planeshift;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox

 */
public class MarshCrocodile extends CardImpl {

    static final private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("blue or black creature you control");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLUE), new ColorPredicate(ObjectColor.BLACK)));
    }

    public MarshCrocodile(UUID ownerId) {
        super(ownerId, 115, "Marsh Crocodile", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.expansionSetCode = "PLS";
        this.subtype.add("Crocodile");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Marsh Crocodile enters the battlefield, return a blue or black creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), false));
        // When Marsh Crocodile enters the battlefield, each player discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(), false));
    }

    public MarshCrocodile(final MarshCrocodile card) {
        super(card);
    }

    @Override
    public MarshCrocodile copy() {
        return new MarshCrocodile(this);
    }
}
