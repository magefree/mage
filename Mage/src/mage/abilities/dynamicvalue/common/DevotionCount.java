/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.constants.ManaType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Each colored mana symbol (e.g. {U}) in the mana costs of permanents you control counts toward your devotion to that color.
 *
 * @author LevelX2
 */
public class DevotionCount implements DynamicValue {

    private ManaType devotionColor;


    public DevotionCount(ManaType devotionColor) {
        this.devotionColor = devotionColor;
    }

    public DevotionCount(final DevotionCount dynamicValue) {
        this.devotionColor = dynamicValue.devotionColor;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int devotion = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(sourceAbility.getControllerId())) {
            switch (devotionColor) {
                case BLACK:
                    devotion += permanent.getManaCost().getMana().getBlack();
                    break;
                case BLUE:
                    devotion += permanent.getManaCost().getMana().getBlue();
                    break;
                case GREEN:
                    devotion += permanent.getManaCost().getMana().getGreen();
                    break;
                case RED:
                    devotion += permanent.getManaCost().getMana().getRed();
                    break;
                case WHITE:
                    devotion += permanent.getManaCost().getMana().getWhite();
                    break;
            }
        }
        return devotion;
    }

    @Override
    public DynamicValue copy() {
        return new DevotionCount(this);
    }

    @Override
    public String toString() {
        return new StringBuilder("devotion to ").append(devotionColor.toString()).toString();
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
