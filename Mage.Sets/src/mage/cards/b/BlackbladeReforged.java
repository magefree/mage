/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.EquipFilterAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;

import java.util.UUID;

/**
 * @author Rystan
 */
public final class BlackbladeReforged extends CardImpl {

    private static final DynamicValue count
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("legendary creature");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public BlackbladeReforged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each land you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(count, count)));

        // Equip legendary creature (3)
        this.addAbility(new EquipFilterAbility(filter, new GenericManaCost(3)));

        // Equip {7}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(7)));
    }

    private BlackbladeReforged(final BlackbladeReforged card) {
        super(card);
    }

    @Override
    public BlackbladeReforged copy() {
        return new BlackbladeReforged(this);
    }
}
