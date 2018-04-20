/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.target.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.common.FilterPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public class TargetPlayerOrPlaneswalker extends TargetPermanentOrPlayer {

    public TargetPlayerOrPlaneswalker() {
        this(1, 1, new FilterPlayerOrPlaneswalker(), false);
    }

    public TargetPlayerOrPlaneswalker(int numTargets) {
        this(numTargets, numTargets, new FilterPlayerOrPlaneswalker(), false);
    }

    public TargetPlayerOrPlaneswalker(FilterPlayerOrPlaneswalker filter) {
        this(1, 1, filter, false);
    }

    public TargetPlayerOrPlaneswalker(int minNumTargets, int maxNumTargets, FilterPlayerOrPlaneswalker filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetPlayerOrPlaneswalker(final TargetPlayerOrPlaneswalker target) {
        super(target);
    }

    public FilterPlaneswalkerPermanent getPlaneswalkerFilter() {
        return filter.getPlaneswalkerFilter();
    }

    @Override
    public TargetPlayerOrPlaneswalker copy() {
        return new TargetPlayerOrPlaneswalker(this);
    }
}
