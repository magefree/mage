/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterObject;
import mage.game.Game;

/**
 *
 * @author jwadsworth
 */
public class TargetObjectMatchesFilterCondition implements Condition {

    private final FilterObject FILTER;
    private final String text;

    public TargetObjectMatchesFilterCondition(FilterObject filter) {
        this(null, filter);
    }

    public TargetObjectMatchesFilterCondition(String text, FilterObject filter) {
        this.FILTER = filter;
        this.text = text;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            MageObject mageObject = game.getObject(source.getFirstTarget());
            if (mageObject != null) {
                return FILTER.match(mageObject, game);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (text != null) {
            return text;
        }
        return super.toString();
    }
}
