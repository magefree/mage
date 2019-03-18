
package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class TrainingGrounds extends CardImpl {

    public TrainingGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // Activated abilities of creatures you control cost up to {2} less to activate. This effect can't reduce the amount of mana an ability costs to activate to less than one mana.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TrainingGroundsEffect()));
    }

    public TrainingGrounds(final TrainingGrounds card) {
        super(card);
    }

    @Override
    public TrainingGrounds copy() {
        return new TrainingGrounds(this);
    }
}

class TrainingGroundsEffect extends CostModificationEffectImpl {

    private static final String effectText = "Activated abilities of creatures you control cost {2} less to activate. " +
            "This effect can't reduce the amount of mana an ability costs to activate to less than one mana";

    TrainingGroundsEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    private TrainingGroundsEffect(final TrainingGroundsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null) {
            Mana mana = abilityToModify.getManaCostsToPay().getMana();
            int reduceMax = mana.getGeneric();
            if (reduceMax > 0 && mana.count() == mana.getGeneric()) {
                reduceMax--;
            }
            if (reduceMax > 2) {
                reduceMax = 2;
            }
            if (reduceMax > 0) {
                ChoiceImpl choice = new ChoiceImpl(true);
                Set<String> set = new LinkedHashSet<>();

                for (int i = 0; i <= reduceMax; i++) {
                    set.add(String.valueOf(i));
                }
                choice.setChoices(set);
                choice.setMessage("Reduce ability cost");
                if (!controller.choose(Outcome.Benefit, choice, game)) {
                    return false;
                }
                int reduce = Integer.parseInt(choice.getChoice());
                CardUtil.reduceCost(abilityToModify, reduce);
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                || (abilityToModify.getAbilityType() == AbilityType.MANA && (abilityToModify instanceof ActivatedAbility))) {
            //Activated abilities of creatures you control
            Permanent permanent = game.getPermanent(abilityToModify.getSourceId());
            if (permanent != null && permanent.isControlledBy(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TrainingGroundsEffect copy() {
        return new TrainingGroundsEffect(this);
    }
}
