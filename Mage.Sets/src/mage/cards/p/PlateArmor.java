package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.WardAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterEquipmentPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class PlateArmor extends CardImpl {

    public PlateArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+3 and has ward {1}.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(1)),
                AttachmentType.EQUIPMENT,
                Duration.WhileOnBattlefield,
                "and has ward {1}. <i>(Whenever equipped creature becomes the target of a spell or ability an opponent controls, " +
                        "counter it unless that player pays {1}.)</i>"
        ));
        this.addAbility(ability);

        // Equip {3}. This ability costs {1} less to activate for each other Equipment you control.
        EquipAbility equipAbility = new EquipAbility(3, false);
        equipAbility.setCostAdjuster(PlateArmorAdjuster.instance);
        equipAbility.setCostReduceText("This ability costs {1} less to activate for each other Equipment you control.");
        this.addAbility(equipAbility.addHint(PlateArmorAdjuster.getHint()));
    }

    private PlateArmor(final PlateArmor card) {
        super(card);
    }

    @Override
    public PlateArmor copy() {
        return new PlateArmor(this);
    }
}

enum PlateArmorAdjuster implements CostAdjuster {
    instance;

    private static final FilterEquipmentPermanent filter = new FilterEquipmentPermanent("Other Equipment you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final DynamicValue equipmentCount = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Other Equipment you control", equipmentCount);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            int count = equipmentCount.calculate(game, ability, null);
            CardUtil.reduceCost(ability, count);
        }
    }
}
