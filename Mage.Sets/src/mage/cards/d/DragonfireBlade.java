package mage.cards.d;

import java.util.Comparator;
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
        EquipAbility equipAbility = new EquipAbility(4);
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

enum DragonfireBladeCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        int reduceCount = 0;
        if (game.inCheckPlayableState()) {
            reduceCount = game.getBattlefield().getAllActivePermanents(ability.getControllerId())
                    .stream()
                    .filter(Permanent::isCreature)
                    .mapToInt(permanent -> permanent.getColor(game).getColorCount())
                    .max()
                    .orElse(reduceCount);
        }
        else {
            Permanent target = game.getPermanent(ability.getFirstTarget());
            if (target != null) {
                reduceCount = target.getColor(game).getColorCount();
            }

        }
        CardUtil.reduceCost(ability, reduceCount);
    }
}