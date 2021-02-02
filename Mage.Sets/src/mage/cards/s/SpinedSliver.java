
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.combat.CombatGroup;

/**
 *
 * @author KholdFuzion
 */
public final class SpinedSliver extends CardImpl {

    public SpinedSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Sliver becomes blocked, that Sliver gets +1/+1 until end of turn for each creature blocking it.
        BlockersCount value = new BlockersCount();
        Effect effect = new BoostTargetEffect(value, value, Duration.EndOfTurn, true);
        effect.setText("it gets +1/+1 until end of turn for each creature blocking it");
        this.addAbility(new BecomesBlockedAllTriggeredAbility(effect, false, StaticFilters.FILTER_PERMANENT_CREATURE_SLIVERS, true));
    }

    private SpinedSliver(final SpinedSliver card) {
        super(card);
    }

    @Override
    public SpinedSliver copy() {
        return new SpinedSliver(this);
    }
}

class BlockersCount implements DynamicValue {

    private final String message;

    public BlockersCount() {
        this.message = "each creature blocking it";
    }

    public BlockersCount(final BlockersCount blockersCount) {
        super();
        this.message = blockersCount.message;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        UUID attackerId = effect.getTargetPointer().getFirst(game, sourceAbility);
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(attackerId)) {
                return combatGroup.getBlockers().size();
            }
        }
        return 0;
    }

    @Override
    public BlockersCount copy() {
        return new BlockersCount(this);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "X";
    }
}
