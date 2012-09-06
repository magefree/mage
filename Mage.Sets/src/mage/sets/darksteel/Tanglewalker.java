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
package mage.sets.darksteel;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DefendingPlayerControlsCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UnblockableAllEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author North
 */
public class Tanglewalker extends CardImpl<Tanglewalker> {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public Tanglewalker(UUID ownerId) {
        super(ownerId, 86, "Tanglewalker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "DST";
        this.subtype.add("Dryad");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each creature you control is unblockable as long as defending player controls an artifact land.
        Effect effect = new ConditionalContinousEffect(
                new UnblockableAllEffect(new FilterControlledCreaturePermanent("Creatures you control"), Duration.WhileOnBattlefield),
                new DefendingPlayerControlsCondition(filter),
                "Each creature you control is unblockable as long as defending player controls an artifact land");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public Tanglewalker(final Tanglewalker card) {
        super(card);
    }

    @Override
    public Tanglewalker copy() {
        return new Tanglewalker(this);
    }
}
