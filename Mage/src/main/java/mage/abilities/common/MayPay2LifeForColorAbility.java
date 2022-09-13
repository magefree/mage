package mage.abilities.common;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class MayPay2LifeForColorAbility extends StaticAbility {

    private final ObjectColor color;

    public MayPay2LifeForColorAbility(String color) {
        super(Zone.BATTLEFIELD, new MayPay2LifeEffect(color));
        this.color = new ObjectColor(color);
    }

    private MayPay2LifeForColorAbility(final MayPay2LifeForColorAbility ability) {
        super(ability);
        this.color = ability.color;
    }

    @Override
    public MayPay2LifeForColorAbility copy() {
        return new MayPay2LifeForColorAbility(this);
    }

    @Override
    public String getRule() {
        return "As an additional cost to cast " + color.getDescription() +
                " permanent spells, you may pay 2 life. Those spells cost {" + color +
                "} less to cast if you paid life this way. This effect reduces only " +
                "the amount of " + color.getDescription() + " mana you pay.";
    }
}

class MayPay2LifeEffect extends CostModificationEffectImpl {

    private final String color;

    MayPay2LifeEffect(String color) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.color = color;
    }

    private MayPay2LifeEffect(final MayPay2LifeEffect effect) {
        super(effect);
        this.color = effect.color;
    }

    @Override
    public MayPay2LifeEffect copy() {
        return new MayPay2LifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(source.getControllerId());
        Cost cost = new PayLifeCost(2);
        if (cost.canPay(abilityToModify, source, source.getControllerId(), game)
                && player.chooseUse(outcome, "Pay 2 life to reduce the cost by {" + color + "}?", source, game)) {
            if (cost.pay(abilityToModify, game, source, source.getControllerId(), true)) {
                CardUtil.reduceCost((SpellAbility) abilityToModify, new ManaCostsImpl<>("{" + color + "}"));
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)
                || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        return spellCard.isPermanent(game) && spellCard.getColor(game).toString().contains(color);
    }
}
