package mage.abilities.dynamicvalue.common;

import mage.ConditionalMana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.ManaType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ManaTypeInManaPoolCount implements DynamicValue {

    private ManaType manaType;

    public ManaTypeInManaPoolCount(ManaType manaType) {
        this.manaType = manaType;
    }

    protected ManaTypeInManaPoolCount(final ManaTypeInManaPoolCount dynamicValue) {
        this.manaType = dynamicValue.manaType;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            amount = player.getManaPool().get(manaType);
            for (ConditionalMana mana : player.getManaPool().getConditionalMana()) {
                amount += mana.get(manaType);
            }
        }
        return amount;
    }

    @Override
    public ManaTypeInManaPoolCount copy() {
        return new ManaTypeInManaPoolCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("unspent ");
        switch (manaType) {
            case BLACK:
                sb.append("black");
                break;
            case GREEN:
                sb.append("green");
                break;
            case RED:
                sb.append("red");
                break;
            case BLUE:
                sb.append("blue");
                break;
            case WHITE:
                sb.append("white");
                break;
            case COLORLESS:
                sb.append("colorless");
                break;
        }
        sb.append(" mana you have");
        return sb.toString();
    }
}
