package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author LevelX2
 */
public final class Conflagrate extends CardImpl {

    public Conflagrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Conflagrate deals X damage divided as you choose among any number of targets.
        DynamicValue xValue = new ConflagrateVariableValue();
        this.getSpellAbility().addEffect(new DamageMultiEffect(xValue));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(xValue));

        // Flashback-{R}{R}, Discard X cards.
        Ability ability = new FlashbackAbility(this, new ManaCostsImpl<>("{R}{R}"));
        ability.addCost(new DiscardXTargetCost(StaticFilters.FILTER_CARD_CARDS));
        this.addAbility(ability);

    }

    private Conflagrate(final Conflagrate card) {
        super(card);
    }

    @Override
    public Conflagrate copy() {
        return new Conflagrate(this);
    }
}

class ConflagrateVariableValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int xValue = sourceAbility.getManaCostsToPay().getX();
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof DiscardXTargetCost) {
                xValue = ((DiscardXTargetCost) cost).getAmount();
            }
        }
        return xValue;
    }

    @Override
    public ConflagrateVariableValue copy() {
        return new ConflagrateVariableValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
