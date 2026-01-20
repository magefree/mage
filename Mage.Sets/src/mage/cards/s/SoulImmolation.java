package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulImmolation extends CardImpl {

    public SoulImmolation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // As an additional cost to cast this spell, blight X. X can't be greater than the greatest toughness among creatures you control.
        this.getSpellAbility().addCost(new SoulImmolationCost());
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.TOUGHNESS_CONTROLLED_CREATURES.getHint());

        // Soul Immolation deals X damage to each opponent and each creature they control.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(GetXValue.instance, TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new DamageAllEffect(
                GetXValue.instance, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES
        ).setText("{this} deals X damage to each opponent and each creature they control"));
    }

    private SoulImmolation(final SoulImmolation card) {
        super(card);
    }

    @Override
    public SoulImmolation copy() {
        return new SoulImmolation(this);
    }
}

class SoulImmolationCost extends VariableCostImpl {

    SoulImmolationCost() {
        super(VariableCostType.ADDITIONAL, "amount to blight");
        this.setText("blight X. X can't be greater than the greatest toughness among creatures you control.");
    }

    private SoulImmolationCost(final SoulImmolationCost cost) {
        super(cost);
    }

    @Override
    public SoulImmolationCost copy() {
        return new SoulImmolationCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new BlightCost(xValue);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return GreatestAmongPermanentsValue.TOUGHNESS_CONTROLLED_CREATURES.calculate(game, source, null);
    }
}
