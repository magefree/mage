/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.common.SimpleStaticAbility;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.EquipLegendaryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Rystan
 */
public final class BlackbladeReforged extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public BlackbladeReforged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each land you control.
        PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(count, count)));

        // Equip legendary creature (3)
        this.addAbility(new EquipLegendaryAbility(Outcome.AddAbility, new GenericManaCost(3)));

        // Equip {7}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(7)));
    }

    public BlackbladeReforged(final BlackbladeReforged card) {
        super(card);
    }

    @Override
    public BlackbladeReforged copy() {
        return new BlackbladeReforged(this);
    }
}
