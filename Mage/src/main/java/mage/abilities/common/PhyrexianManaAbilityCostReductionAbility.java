package mage.abilities.common;

import mage.abilities.Ability;
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
 * Phyrexian mana functions like a cost reduction, as whether to pay is determined before an ability's cost is "locked-in".
 * Thus, it will be affected by Training Ground and Supression Field.
 */
public class PhyrexianManaAbilityCostReductionAbility extends SimpleStaticAbility {

    public PhyrexianManaAbilityCostReductionAbility(ManaCosts<ManaCost> manaCosts, UUID abilityId) {
        super(Zone.ALL, null);
        for(ManaCost manaCost : manaCosts) {
            if(manaCost instanceof PhyrexianManaCost) {
                addEffect(new PhyrexianManaAbilityCostReductionEffect((PhyrexianManaCost) manaCost, abilityId));
            }
        }
    }

    class PhyrexianManaAbilityCostReductionEffect extends CostModificationEffectImpl {

        private final ManaCosts<ManaCost> manaCostsToReduce;
        private final UUID abilityId;

        PhyrexianManaAbilityCostReductionEffect(PhyrexianManaCost phyrexianManaCost, UUID abilityId) {
            super(Duration.WhileOnStack, Outcome.Neutral, CostModificationType.REDUCE_COST);
            this.abilityId = abilityId;
            this.manaCostsToReduce = new ManaCostsImpl<>(phyrexianManaCost.getBaseText());
            staticText = "(" + phyrexianManaCost.getText() + " can be paid with either " + manaCostsToReduce.getText() + " or 2 life.)";
        }

        PhyrexianManaAbilityCostReductionEffect(PhyrexianManaAbilityCostReductionEffect effect) {
            super(effect);
            this.abilityId = effect.abilityId;
            this.manaCostsToReduce = effect.manaCostsToReduce;
        }

        @Override
        public boolean apply(Game game, Ability source, Ability abilityToModify) {
            Player controller = game.getPlayer(abilityToModify.getControllerId());
            if (controller != null && manaCostsToReduce != null) {
                if(controller.chooseUse(Outcome.Neutral,  "Pay 2 life to reduce the cost by " + manaCostsToReduce.getText() + "?", source, game)) {
                    game.getPlayer(controller.getId()).loseLife(2, game, false);
                    CardUtil.reduceCost(abilityToModify, manaCostsToReduce);
                }

                return true;
            }

            return false;
        }

        @Override
        public boolean applies(Ability abilityToModify, Ability source, Game game) {
            Player controller = game.getPlayer(abilityToModify.getControllerId());
            UUID controllerId = controller.getId();
            return game.getPlayer(controllerId).isLifeTotalCanChange() && game.getPlayer(controllerId).getLife() >= 2  && abilityToModify.getOriginalId().equals(abilityId);
        }

        @Override
        public PhyrexianManaAbilityCostReductionEffect copy() {
            return new PhyrexianManaAbilityCostReductionEffect(this);
        }
    }
}
