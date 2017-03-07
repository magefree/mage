package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * As explained by https://blogs.magicjudges.org/rulestips/2012/08/how-trinisphere-works-with-phyrexian-mana/
 * Phyrexian mana functions like a cost reduction, as whether to pay is determined before a spell's cost is "locked-in".
 * Thus, it will be affected by Trinisphere.
 */
public class PhyrexianManaSpellCostReductionAbility extends SimpleStaticAbility {

    public PhyrexianManaSpellCostReductionAbility(ManaCosts<ManaCost> manaCosts) {
        super(Zone.STACK, null);
        for(ManaCost manaCost : manaCosts) {
            if(manaCost instanceof PhyrexianManaCost) {
                addEffect(new PhyrexianManaSpellCostReductionEffect((PhyrexianManaCost) manaCost));
            }
        }
    }

    class PhyrexianManaSpellCostReductionEffect extends CostModificationEffectImpl {

        private final ManaCosts<ManaCost> manaCostsToReduce;

        PhyrexianManaSpellCostReductionEffect(PhyrexianManaCost phyrexianManaCost) {
            super(Duration.WhileOnStack, Outcome.Neutral, CostModificationType.REDUCE_COST);
            this.manaCostsToReduce = new ManaCostsImpl<>(phyrexianManaCost.getBaseText());
            staticText = "(" + phyrexianManaCost.getText() + " can be paid with either " + manaCostsToReduce.getText() + " or 2 life.)";
        }

        PhyrexianManaSpellCostReductionEffect(PhyrexianManaSpellCostReductionEffect effect) {
            super(effect);
            this.manaCostsToReduce = effect.manaCostsToReduce;
        }

        @Override
        public boolean apply(Game game, Ability source, Ability abilityToModify) {
            Player controller = game.getPlayer(abilityToModify.getControllerId());
            if (controller != null && manaCostsToReduce != null) {
                if(controller.chooseUse(Outcome.Benefit,  "Pay 2 life to reduce the cost by " + manaCostsToReduce.getText() + "?", source, game)) {
                    game.getPlayer(controller.getId()).loseLife(2, game, false);
                    CardUtil.reduceCost(abilityToModify, manaCostsToReduce);
                }

                return true;
            }

            return false;
        }

        @Override
        public boolean applies(Ability abilityToModify, Ability source, Game game) {
            if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
                Player controller = game.getPlayer(abilityToModify.getControllerId());
                UUID controllerId = controller.getId();
                return game.getPlayer(controllerId).isLifeTotalCanChange() && game.getCard(abilityToModify.getSourceId()) != null && game.getPlayer(controllerId).getLife() >= 2;
            }
            return false;
        }

        @Override
        public PhyrexianManaSpellCostReductionEffect copy() {
            return new PhyrexianManaSpellCostReductionEffect(this);
        }
    }
}
