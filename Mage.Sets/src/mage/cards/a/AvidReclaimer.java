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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author spjspj
 */
public class AvidReclaimer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new SubtypePredicate(SubType.NISSA));
    }

    public AvidReclaimer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add("Human");
        this.subtype.add("Druid");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {G} or {U} to your mana pool. If you control a Nissa planeswalker, you gain 2 life.
        Ability GreenManaAbility = new GreenManaAbility();
        GreenManaAbility.addEffect(new ConditionalOneShotEffect(new GainLifeEffect(2), new PermanentsOnTheBattlefieldCondition(filter), "If you control a Nissa planeswalker, you gain 2 life."));
        this.addAbility(GreenManaAbility);

        Ability BlueManaAbility = new BlueManaAbility();
        BlueManaAbility.addEffect(new ConditionalOneShotEffect(new GainLifeEffect(2), new PermanentsOnTheBattlefieldCondition(filter), "If you control a Nissa planeswalker, you gain 2 life."));
        this.addAbility(BlueManaAbility);
    }

    public AvidReclaimer(final AvidReclaimer card) {
        super(card);
    }

    @Override
    public AvidReclaimer copy() {
        return new AvidReclaimer(this);
    }
}
