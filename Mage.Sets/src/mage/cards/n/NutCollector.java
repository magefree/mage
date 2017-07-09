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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.SquirrelToken;

/**
 *
 * @author cbt33
 */
public class NutCollector extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("squirrel");

    static {
        filter.add(new SubtypePredicate(SubType.SQUIRREL));
    }

    public NutCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add("Human");
        this.subtype.add("Druid");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may create a 1/1 green Squirrel creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SquirrelToken()), TargetController.YOU, true));
        // Threshold - Squirrel creatures get +2/+2 as long as seven or more cards are in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, false),
            new CardsInControllerGraveCondition(7), "Squirrel creatures get +2/+2 as long as seven or more cards are in your graveyard"));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public NutCollector(final NutCollector card) {
        super(card);
    }

    @Override
    public NutCollector copy() {
        return new NutCollector(this);
    }
}
