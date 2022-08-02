package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;

import java.util.UUID;

/**
 * @author KholdFuzion
 */
public final class SpinedSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SLIVER, "a Sliver");

    public SpinedSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Sliver becomes blocked, that Sliver gets +1/+1 until end of turn for each creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new BoostTargetEffect(BlockersCount.instance, BlockersCount.instance, Duration.EndOfTurn)
                        .setText("that Sliver gets +1/+1 until end of turn for each creature blocking it"),
                false, filter, true
        ));
    }

    private SpinedSliver(final SpinedSliver card) {
        super(card);
    }

    @Override
    public SpinedSliver copy() {
        return new SpinedSliver(this);
    }
}

enum BlockersCount implements DynamicValue {
    instance;

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
        return this;
    }

    @Override
    public String getMessage() {
        return "creature blocking it";
    }

    @Override
    public String toString() {
        return "1";
    }
}
