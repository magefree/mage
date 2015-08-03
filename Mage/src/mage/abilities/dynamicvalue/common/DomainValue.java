package mage.abilities.dynamicvalue.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class DomainValue implements DynamicValue {

    private Integer amount;
    private boolean countTargetPlayer;
    private UUID player;

    public DomainValue() {
        this(1);
    }

    public DomainValue(boolean countTargetPlayer) {
        this(1, countTargetPlayer);
    }

    public DomainValue(Integer amount) {
        this(amount, false);
    }

    public DomainValue(Integer amount, boolean countTargetPlayer) {
        this.amount = amount;
        this.countTargetPlayer = countTargetPlayer;
    }

    public DomainValue(Integer amount, UUID player) {
        this(amount, false);
        this.player = player;
    }

    public DomainValue(final DomainValue dynamicValue) {
        this.amount = dynamicValue.amount;
        this.countTargetPlayer = dynamicValue.countTargetPlayer;
        this.player = dynamicValue.player;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int havePlains = 0;
        int haveIslands = 0;
        int haveMountains = 0;
        int haveSwamps = 0;
        int haveForests = 0;
        UUID targetPlayer;
        if(player != null) {
            targetPlayer = player;
        }
        else if(countTargetPlayer) {
            targetPlayer = sourceAbility.getTargets().getFirstTarget();
        } else {
            targetPlayer = sourceAbility.getControllerId();
        }
        for (Permanent p : game.getBattlefield().getAllActivePermanents(targetPlayer)) {
            if (p.getCardType().contains(CardType.LAND)) {
                if (havePlains == 0 && p.getSubtype().contains("Plains")) {
                    havePlains = 1;
                }
                if (haveIslands == 0 && p.getSubtype().contains("Island")) {
                    haveIslands = 1;
                }
                if (haveMountains == 0 && p.getSubtype().contains("Mountain")) {
                    haveMountains = 1;
                }
                if (haveSwamps == 0 && p.getSubtype().contains("Swamp")) {
                    haveSwamps = 1;
                }
                if (haveForests == 0 && p.getSubtype().contains("Forest")) {
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
        return amount.toString();
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public String getMessage() {
        return "basic land type among lands you control";
    }
}
