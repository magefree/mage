package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.BuybackCondition;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public final class MemoryCrystal extends CardImpl {

    public MemoryCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Buyback costs cost {2} less.
        this.addAbility(new SimpleStaticAbility(new MemoryCrystalSpellsCostReductionEffect()));
    }

    private MemoryCrystal(final MemoryCrystal card) {
        super(card);
    }

    @Override
    public MemoryCrystal copy() {
        return new MemoryCrystal(this);
    }
}

class MemoryCrystalSpellsCostReductionEffect extends CostModificationEffectImpl {

    MemoryCrystalSpellsCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Buyback costs cost {2} less.";
    }

    private MemoryCrystalSpellsCostReductionEffect(final MemoryCrystalSpellsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Card card = game.getCard(abilityToModify.getSourceId());
        if (card != null) {
            for (Ability ability : card.getAbilities(game)) {
                if (ability instanceof BuybackAbility && ability.isActivated()) {
                    int amountToReduce = ((BuybackAbility) ability).reduceCost(2);
                    CardUtil.reduceCost(abilityToModify, amountToReduce);
                }
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            return game.getSpell(abilityToModify.getId()) != null && BuybackCondition.instance.apply(game, abilityToModify);
        }
        return false;
    }

    @Override
    public MemoryCrystalSpellsCostReductionEffect copy() {
        return new MemoryCrystalSpellsCostReductionEffect(this);
    }
}
