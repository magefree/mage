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
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ..AS IS.. AND ANY EXPRESS OR IMPLIED
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
package mage.game.command.planes;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.util.CardUtil;
import mage.watchers.common.PlanarRollWatcher;

/**
 *
 * @author spjspj
 */
public class TurriIslandPlane extends Plane {

    public TurriIslandPlane() {
        this.setName("Plane - Turri Island");
        this.setExpansionSetCodeForImage("PCA");

        // Creature spells cost {2} less to cast.
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new TurriIslandEffect(2));
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, reveal the top three cards of your library. Put all creature cards revealed this way into your hand and the rest into your graveyard.
        Effect chaosEffect = new RevealLibraryPutIntoHandEffect(3, new FilterCreatureCard("creature cards"), Zone.GRAVEYARD);
        Target chaosTarget = null;

        List<Effect> chaosEffects = new ArrayList<Effect>();
        chaosEffects.add(chaosEffect);
        List<Target> chaosTargets = new ArrayList<Target>();
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }
}

class TurriIslandEffect extends CostModificationEffectImpl {

    private static final FilterCard filter = new FilterCard("creature spells");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    private static final String rule = "Creature spells cost {2} less to cast";
    private int amount = 2;

    public TurriIslandEffect(int amount) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = 2;
        this.staticText = rule;
    }
    
    protected TurriIslandEffect(TurriIslandEffect effect) {
        super(effect);
        this.amount = effect.amount;        
    }    

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, this.amount);
        return true;
    }

    /**
     * Overwrite this in effect that inherits from this
     *
     * @param card
     * @param source
     * @param game
     * @return
     */
    protected boolean selectedByRuntimeData(Card card, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Plane cPlane = game.getState().getCurrentPlane();
            if (cPlane == null) {
                return false;
            }
            if (cPlane != null) {
                if (!cPlane.getName().equalsIgnoreCase("Plane - Turri Island")) {
                    return false;
                }
            }
            
            Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
            if (spell != null) {
                return this.filter.match(spell, game) && selectedByRuntimeData(spell, source, game);
            } else {
                // used at least for flashback ability because Flashback ability doesn't use stack
                Card sourceCard = game.getCard(abilityToModify.getSourceId());
                return sourceCard != null && this.filter.match(sourceCard, game) && selectedByRuntimeData(sourceCard, source, game);
            }
        }
        return false;
    }

    @Override
    public TurriIslandEffect copy() {
        return new TurriIslandEffect(this);
    }
}
