package mage.abilities.common;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
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

    private final String rule;

    public MayPay2LifeForColorAbility(ObjectColor color) {
        super(Zone.BATTLEFIELD, new MayPay2LifeEffect(color));
        this.rule = makeRule(color);
    }

    private MayPay2LifeForColorAbility(final MayPay2LifeForColorAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public MayPay2LifeForColorAbility copy() {
        return new MayPay2LifeForColorAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }

    private static String makeRule(ObjectColor color) {
        return "As an additional cost to cast " + color.getDescription() +
                " permanent spells, you may pay 2 life. Those spells cost {" + color +
                "} less to cast if you paid life this way. This effect reduces only " +
                "the amount of " + color.getDescription() + " mana you pay.";
    }
}

class MayPay2LifeEffect extends CostModificationEffectImpl {

    private final ObjectColor color;
    private final ManaCosts<ManaCost> manaCost;

    MayPay2LifeEffect(ObjectColor color) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.color = color;
        this.manaCost = new ManaCostsImpl<>("{" + color + "}");
    }

    private MayPay2LifeEffect(final MayPay2LifeEffect effect) {
        super(effect);
        this.color = effect.color;
        this.manaCost = effect.manaCost;
    }

    @Override
    public MayPay2LifeEffect copy() {
        return new MayPay2LifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player player = game.getPlayer(abilityToModify.getControllerId());
        Cost cost = new PayLifeCost(2);
        if (!cost.canPay(abilityToModify, source, source.getControllerId(), game)) {
            return true;
        }
        if (game.inCheckPlayableState() || (
                player.chooseUse(outcome, "Pay 2 life to reduce the cost by {" + color + "}?", source, game)
                        && cost.pay(abilityToModify, game, source, source.getControllerId(), true)
        )) {
            CardUtil.adjustCost((SpellAbility) abilityToModify, manaCost, false);
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
        return spellCard.isPermanent(game) && spellCard.getColor(game).contains(color);
    }
}
