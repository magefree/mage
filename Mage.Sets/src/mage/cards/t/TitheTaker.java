package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.AfterlifeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitheTaker extends CardImpl {

    public TitheTaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // During your turn, spells your opponents cast cost {1} more to cast and abilities your opponents activate cost {1} more to activate unless they're mana abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TitheTakerCostReductionEffect())
                .addHint(MyTurnHint.instance));

        // Afterlife 1
        this.addAbility(new AfterlifeAbility(1));
    }

    private TitheTaker(final TitheTaker card) {
        super(card);
    }

    @Override
    public TitheTaker copy() {
        return new TitheTaker(this);
    }
}

class TitheTakerCostReductionEffect extends CostModificationEffectImpl {

    TitheTakerCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "During your turn, spells your opponents cast cost {1} more to cast " +
                "and abilities your opponents activate cost {1} more to activate unless they're mana abilities.";
    }

    private TitheTakerCostReductionEffect(TitheTakerCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (abilityToModify.getAbilityType() == AbilityType.SPELL) {
            SpellAbility spellAbility = (SpellAbility) abilityToModify;
            CardUtil.adjustCost(spellAbility, -1);
        }
        if (abilityToModify.getAbilityType() == AbilityType.ACTIVATED) {
            CardUtil.increaseCost(abilityToModify, 1);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!MyTurnCondition.instance.apply(game, source)) {
            return false;
        }
        if (!(abilityToModify.getAbilityType() == AbilityType.SPELL)
                && !(abilityToModify.getAbilityType() == AbilityType.ACTIVATED)) {
            return false;
        }
        return game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId());
    }

    @Override
    public TitheTakerCostReductionEffect copy() {
        return new TitheTakerCostReductionEffect(this);
    }

}