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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.k.KrallenhordeHowler;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 * @author fireshoes
 */
public class DuskwatchRecruiter extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature card");

    public DuskwatchRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.subtype.add("Werewolf");

        this.transformable = true;
        this.secondSideCardClazz = KrallenhordeHowler.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{G}: Look at the top three cards of your library. You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new LookLibraryAndPickControllerEffect(3, 1, filter, true, false, Zone.HAND, true),
                new ManaCostsImpl("{2}{G}")));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Duskwatch Recruiter.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public DuskwatchRecruiter(final DuskwatchRecruiter card) {
        super(card);
    }

    @Override
    public DuskwatchRecruiter copy() {
        return new DuskwatchRecruiter(this);
    }

}
