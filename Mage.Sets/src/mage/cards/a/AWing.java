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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.RemoveFromCombatSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public class AWing extends CardImpl {

    public AWing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{R}");
        this.subtype.add("Rebel");
        this.subtype.add("Starship");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // {1}:Remove A-wing from combat. It must attack on your next combat if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RemoveFromCombatSourceEffect(), new GenericManaCost(1));
        ability.addEffect(new AWingAttacksNextCombatIfAbleSourceEffect());
        this.addAbility(ability);
    }

    public AWing(final AWing card) {
        super(card);
    }

    @Override
    public AWing copy() {
        return new AWing(this);
    }
}

class AWingAttacksNextCombatIfAbleSourceEffect extends RequirementEffect {

    int turnNumber;
    int phaseCount;
    int nextPhaseTurnNumber = 0;
    int nextPhasePhaseCount = 0;

    public AWingAttacksNextCombatIfAbleSourceEffect() {
        super(Duration.Custom);
        staticText = "It must attack on your next combat if able";
    }

    public AWingAttacksNextCombatIfAbleSourceEffect(final AWingAttacksNextCombatIfAbleSourceEffect effect) {
        super(effect);
        this.turnNumber = effect.turnNumber;
        this.phaseCount = effect.phaseCount;
        this.nextPhaseTurnNumber = effect.nextPhaseTurnNumber;
        this.nextPhasePhaseCount = effect.nextPhasePhaseCount;
    }

    @Override
    public void init(Ability source, Game game) {
        turnNumber = game.getTurnNum();
        phaseCount = game.getPhase().getCount();
    }

    @Override
    public AWingAttacksNextCombatIfAbleSourceEffect copy() {
        return new AWingAttacksNextCombatIfAbleSourceEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (game.getTurnNum() != turnNumber || game.getPhase().getCount() != phaseCount) {
                if (nextPhaseTurnNumber == 0) {
                    nextPhasePhaseCount = game.getPhase().getCount();
                    nextPhaseTurnNumber = game.getTurnNum();
                } else if (game.getTurnNum() != nextPhaseTurnNumber || game.getPhase().getCount() != nextPhasePhaseCount) {
                    this.discard();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}
