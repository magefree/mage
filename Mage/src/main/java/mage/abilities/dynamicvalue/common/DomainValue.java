package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Loki
 */
public class DomainValue implements DynamicValue {

    private int amount;
    private boolean countTargetPlayer;
    private UUID playerId;

    public DomainValue() {
        this(1);
    }

    public DomainValue(boolean countTargetPlayer) {
        this(1, countTargetPlayer);
    }

    public DomainValue(int amount) {
        this(amount, false);
    }

    public DomainValue(int amount, boolean countTargetPlayer) {
        this.amount = amount;
        this.countTargetPlayer = countTargetPlayer;
    }

    public DomainValue(int amount, UUID playerId) {
        this(amount, false);
        this.playerId = playerId;
    }

    public DomainValue(final DomainValue dynamicValue) {
        this.amount = dynamicValue.amount;
        this.countTargetPlayer = dynamicValue.countTargetPlayer;
        this.playerId = dynamicValue.playerId;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int havePlains = 0;
        int haveIslands = 0;
        int haveMountains = 0;
        int haveSwamps = 0;
        int haveForests = 0;
        UUID targetPlayer;
        if (playerId != null) {
            targetPlayer = playerId;
        } else if (countTargetPlayer) {
            targetPlayer = effect.getTargetPointer().getFirst(game, sourceAbility);
        } else {
            targetPlayer = sourceAbility.getControllerId();
        }
        for (Permanent p : game.getBattlefield().getAllActivePermanents(targetPlayer)) {
            if (p.isLand()) {
                if (havePlains == 0 && p.hasSubtype(SubType.PLAINS, game)) {
                    havePlains = 1;
                }
                if (haveIslands == 0 && p.hasSubtype(SubType.ISLAND, game)) {
                    haveIslands = 1;
                }
                if (haveMountains == 0 && p.hasSubtype(SubType.MOUNTAIN, game)) {
                    haveMountains = 1;
                }
                if (haveSwamps == 0 && p.hasSubtype(SubType.SWAMP, game)) {
                    haveSwamps = 1;
                }
                if (haveForests == 0 && p.hasSubtype(SubType.FOREST, game)) {
                    haveForests = 1;
                }
            }
        }
        return amount * (haveForests + haveIslands + haveMountains + havePlains + haveSwamps);
    }

    @Override
    public DomainValue copy() {
        return new DomainValue(this);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String getMessage() {
        return "basic land type among lands " + (countTargetPlayer ? "he or she controls" : "you control");
    }
}
