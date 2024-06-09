package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class TrainingGrounds extends CardImpl {

    public TrainingGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // Activated abilities of creatures you control cost up to {2} less to activate. 
        // This effect can't reduce the amount of mana an ability costs to activate to less than one mana.
        this.addAbility(new SimpleStaticAbility(new TrainingGroundsEffect()));
    }

    private TrainingGrounds(final TrainingGrounds card) {
        super(card);
    }

    @Override
    public TrainingGrounds copy() {
        return new TrainingGrounds(this);
    }
}

class TrainingGroundsEffect extends CostModificationEffectImpl {

    private static final String effectText = "Activated abilities of creatures you control "
            + "cost {2} less to activate. "
            + "This effect can't reduce the mana in that cost to less than one mana";

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
        if (controller == null) {
            return false;
        }
        int reduceMax = CardUtil.calculateActualPossibleGenericManaReduction(abilityToModify.getManaCostsToPay().getMana(), 2, 1);
        if (reduceMax <= 0) {
            return true;
        }
        CardUtil.reduceCost(abilityToModify, reduceMax);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!abilityToModify.isActivatedAbility()) {
            return false;
        }
        //Activated abilities of creatures you control
        Permanent permanent = game.getPermanentOrLKIBattlefield(abilityToModify.getSourceId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public TrainingGroundsEffect copy() {
        return new TrainingGroundsEffect(this);
    }
}
