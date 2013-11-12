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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.turn.Phase;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class HuaTuoHonoredPhysician extends CardImpl<HuaTuoHonoredPhysician> {

    public HuaTuoHonoredPhysician(UUID ownerId) {
        super(ownerId, 149, "Hua Tuo, Honored Physician", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Human");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Put target creature card from your graveyard on top of your library. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(true), new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.getInstance());
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.addAbility(ability);
    }

    public HuaTuoHonoredPhysician(final HuaTuoHonoredPhysician card) {
        super(card);
    }

    @Override
    public HuaTuoHonoredPhysician copy() {
        return new HuaTuoHonoredPhysician(this);
    }
}

class MyTurnBeforeAttackersDeclaredCondition implements Condition {
    private static MyTurnBeforeAttackersDeclaredCondition fInstance = new MyTurnBeforeAttackersDeclaredCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getActivePlayerId().equals(source.getControllerId())) {
            TurnPhase turnPhase = game.getTurn().getPhase().getType();
            if (turnPhase.equals(TurnPhase.BEGINNING) || turnPhase.equals(TurnPhase.PRECOMBAT_MAIN)) {
                return true;
            }
            if (turnPhase.equals(TurnPhase.COMBAT)) {
                return !game.getTurn().isDeclareAttackersStepStarted();
            }
        }
        return false;
    }

    @Override
    public String toString() {
	return "during your turn, before attackers are declared";
    }
}
