package mage.cards.d;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HexproofFromMonocoloredAbility;
import mage.abilities.mana.ManaOptions;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Jmlundeen
 */
public final class DragonfireBlade extends CardImpl {

    public DragonfireBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has hexproof from monocolored.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(HexproofFromMonocoloredAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("and has hexproof from monocolored"));
        this.addAbility(ability);

        // Equip {4}. This ability costs {1} less to activate for each color of the creature it targets.
        DragonfireBladeEquipAbility equipAbility = new DragonfireBladeEquipAbility(4);
        equipAbility.setCostAdjuster(DragonfireBladeCostAdjuster.instance);
        equipAbility.setCostReduceText("This ability costs {1} less to activate for each color of the creature it targets.");
        this.addAbility(equipAbility);
    }

    private DragonfireBlade(final DragonfireBlade card) {
        super(card);
    }

    @Override
    public DragonfireBlade copy() {
        return new DragonfireBlade(this);
    }
}

class DragonfireBladeEquipAbility extends EquipAbility {

    public DragonfireBladeEquipAbility(int cost) {
        super(cost);
    }

    public DragonfireBladeEquipAbility(DragonfireBladeEquipAbility ability) {
        super(ability);
    }

    @Override
    public ManaOptions getMinimumCostToActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        ManaOptions manaOptions = getManaCostsToPay().getOptions(player.canPayLifeCost(this));
        Set<Integer> colorCounts = game.getBattlefield().getAllActivePermanents(playerId)
                .stream()
                .filter(Permanent::isCreature)
                .filter(permanent -> permanent.getColor(game).getColorCount() > 0)
                .map(permanent -> permanent.getColor(game).getColorCount())
                .collect(Collectors.toSet());
        for (int count : colorCounts) {
            int cost = Math.max(0, 4 - count);
            manaOptions.add(new Mana(ManaType.GENERIC, cost));
        }
        return manaOptions;
    }

    @Override
    public DragonfireBladeEquipAbility copy() {
        return new DragonfireBladeEquipAbility(this);
    }
}

enum DragonfireBladeCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        Permanent target = game.getPermanent(ability.getFirstTarget());
        if (target == null) {
            return;
        }
        int colors = target.getColor(game).getColorCount();
        CardUtil.reduceCost(ability, colors);
    }
}