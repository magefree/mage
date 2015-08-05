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
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.PutPermanentOnBattlefieldEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public class WarrenInstigator extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a Goblin creature card");

    static {
        filter.add(new SubtypePredicate("Goblin"));
    }

    public WarrenInstigator(UUID ownerId) {
        super(ownerId, 154, "Warren Instigator", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{R}{R}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Goblin");
        this.subtype.add("Berserker");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever Warren Instigator deals damage to an opponent, you may put a Goblin creature card from your hand onto the battlefield.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new PutPermanentOnBattlefieldEffect(filter), false));
    }

    public WarrenInstigator(final WarrenInstigator card) {
        super(card);
    }

    @Override
    public WarrenInstigator copy() {
        return new WarrenInstigator(this);
    }
}
