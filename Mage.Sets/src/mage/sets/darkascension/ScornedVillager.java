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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;

/**
 *
 * @author North
 */
public class ScornedVillager extends CardImpl<ScornedVillager> {

    public ScornedVillager(UUID ownerId) {
        super(ownerId, 125, "Scorned Villager", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Human");
        this.subtype.add("Werewolf");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.canTransform = true;
        this.secondSideCard = new MoonscarredWerewolf(ownerId);

        // {tap}: Add {G} to your mana pool.
        this.addAbility(new GreenManaAbility());
        // At the beginning of each upkeep, if no spells were cast last turn, transform Scorned Villager.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability,
                NoSpellsWereCastLastTurnCondition.getInstance(),
                TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public ScornedVillager(final ScornedVillager card) {
        super(card);
    }

    @Override
    public ScornedVillager copy() {
        return new ScornedVillager(this);
    }
}
