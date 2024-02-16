package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.Outcome;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author LevelX2
 */
public final class GhostfireBlade extends CardImpl {

    public GhostfireBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 2)));

        // Equip {3}
        Ability ability = new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false);
        ability.setCostAdjuster(GhostfireBladeAdjuster.instance);
        this.addAbility(ability);

        // Ghostfire Blade's equip ability costs {2} less to activate if it targets a colorless creature.
        this.addAbility(new SimpleStaticAbility(new InfoEffect("{this}'s equip ability costs {2} less to activate if it targets a colorless creature")));
    }

    private GhostfireBlade(final GhostfireBlade card) {
        super(card);
    }

    @Override
    public GhostfireBlade copy() {
        return new GhostfireBlade(this);
    }
}

enum GhostfireBladeAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        // checking state
        if (game.inCheckPlayableState()) {
            if (CardUtil
                    .getAllPossibleTargets(ability, game)
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .noneMatch(permanent -> permanent.getColor(game).isColorless())) {
                return;
            }
        } else {
            Permanent permanent = game.getPermanent(ability.getFirstTarget());
            if (permanent == null || !permanent.getColor(game).isColorless()) {
                return;
            }
        }
        CardUtil.reduceCost(ability, 2);
    }
}
