package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HexgoldHoverwings extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures you control that are equipped");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(EquippedPredicate.instance);
    }

    public HexgoldHoverwings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Equipped creature has flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Creatures you control that are equipped get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 0, Duration.WhileOnBattlefield, filter, false
        )));

        // Equip {2}{W}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{2}{W}"), false));
    }

    private HexgoldHoverwings(final HexgoldHoverwings card) {
        super(card);
    }

    @Override
    public HexgoldHoverwings copy() {
        return new HexgoldHoverwings(this);
    }
}
