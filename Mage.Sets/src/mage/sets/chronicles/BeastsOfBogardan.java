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
package mage.sets.chronicles;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author nigelzor
 */
public class BeastsOfBogardan extends CardImpl {

    private static final FilterCard protectionFilter = new FilterCard("red");
    private static final FilterPermanent controlFilter = new FilterPermanent("nontoken white permanent");

    static {
        protectionFilter.add(new ColorPredicate(ObjectColor.RED));
        controlFilter.add(new ColorPredicate(ObjectColor.WHITE));
        controlFilter.add(Predicates.not(new TokenPredicate()));
    }

    public BeastsOfBogardan(UUID ownerId) {
        super(ownerId, 45, "Beasts of Bogardan", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.expansionSetCode = "CHR";
        this.subtype.add("Beast");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protection from red
        this.addAbility(new ProtectionAbility(protectionFilter));
        // Beasts of Bogardan gets +1/+1 as long as an opponent controls a nontoken white permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                new OpponentControlsPermanentCondition(controlFilter),
                "{this} gets +1/+1 as long as an opponent controls a nontoken white permanent")));
    }

    public BeastsOfBogardan(final BeastsOfBogardan card) {
        super(card);
    }

    @Override
    public BeastsOfBogardan copy() {
        return new BeastsOfBogardan(this);
    }
}
