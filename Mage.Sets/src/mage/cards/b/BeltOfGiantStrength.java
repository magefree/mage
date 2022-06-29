package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.effects.common.continuous.SetPowerToughnessEnchantedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.Outcome;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class BeltOfGiantStrength extends CardImpl {

    public BeltOfGiantStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has base power and toughness 10/10.
        this.addAbility(new SimpleStaticAbility(new SetPowerToughnessEnchantedEffect(10, 10)
                .setText("equipped creature has base power and toughness 10/10")));

        // Equip {10}. This ability costs {X} less to activate where X is the power of the creature it targets.
        EquipAbility ability = new EquipAbility(Outcome.BoostCreature, new GenericManaCost(10), new TargetControlledCreaturePermanent(), false);
        ability.setCostReduceText("This ability costs {X} less to activate, where X is the power of the creature it targets.");
        ability.setCostAdjuster(BeltOfGiantStrengthAdjuster.instance);
        this.addAbility(ability);
    }

    private BeltOfGiantStrength(final BeltOfGiantStrength card) {
        super(card);
    }

    @Override
    public BeltOfGiantStrength copy() {
        return new BeltOfGiantStrength(this);
    }
}

enum BeltOfGiantStrengthAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (game.inCheckPlayableState()) {
            int maxPower = 0;
            for (UUID permId : CardUtil.getAllPossibleTargets(ability, game)) {
                Permanent permanent = game.getPermanent(permId);
                if (permanent != null) {
                    int power = permanent.getPower().getValue();
                    if (power > maxPower) {
                        maxPower = power;
                    }
                }
            }
            if (maxPower > 0) {
                CardUtil.reduceCost(ability, maxPower);
            }
        } else {
            Permanent permanent = game.getPermanent(ability.getFirstTarget());
            if (permanent != null) {
                int power = permanent.getPower().getValue();
                if (power > 0) {
                    CardUtil.reduceCost(ability, power);
                }
            }
        }
    }
}
