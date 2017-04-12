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
package mage.cards.d;

import mage.MageInt;
import mage.constants.ComparisonType;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.DragonToken2;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class DragonmasterOutcast extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("land");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public DragonmasterOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, if you control six or more lands, create a 5/5 red Dragon creature token with flying.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new DragonToken2(), 1), TargetController.YOU, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 5), "At the beginning of your upkeep, if you control six or more lands, create a 5/5 red Dragon creature token with flying."));
    }

    public DragonmasterOutcast(final DragonmasterOutcast card) {
        super(card);
    }

    @Override
    public DragonmasterOutcast copy() {
        return new DragonmasterOutcast(this);
    }
}
