/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.common;

import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */

public class CanBlockOnlyFlyingAbility extends SimpleStaticAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public CanBlockOnlyFlyingAbility() {
        super(Zone.BATTLEFIELD, new CantBlockAllEffect(filter, Duration.WhileOnBattlefield));
    }

    private CanBlockOnlyFlyingAbility(CanBlockOnlyFlyingAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "{this} can block only creatures with flying.";
    }

    @Override
    public CanBlockOnlyFlyingAbility copy() {
        return new CanBlockOnlyFlyingAbility(this);
    }
}
