package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeltOfGiantStrength extends CardImpl {

    public BeltOfGiantStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has base power and toughness 10/10.
        this.addAbility(new SimpleStaticAbility(
                new SetBasePowerToughnessAttachedEffect(10, 10, AttachmentType.EQUIPMENT)
        ));

        // Equip {10}. This ability costs {X} less to activate where X is the power of the creature it targets.
        EquipAbility ability = new EquipAbility(Outcome.BoostCreature, new GenericManaCost(10), new TargetControlledCreaturePermanent(), false);
        ability.setCostReduceText("This ability costs {X} less to activate, where X is the power of the creature it targets.");
        ability.setCostAdjuster(BeltOfGiantStrengthCostAdjuster.instance);
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

enum BeltOfGiantStrengthCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        int power;
        if (game.inCheckPlayableState()) {
            power = CardUtil.getAllPossibleTargets(ability, game).stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .mapToInt(p -> p.getPower().getValue())
                    .max().orElse(0);
        } else {
            power = Optional.ofNullable(game.getPermanent(ability.getFirstTarget()))
                    .map(p -> p.getPower().getValue())
                    .orElse(0);
        }
        CardUtil.reduceCost(ability, power);
    }
}
