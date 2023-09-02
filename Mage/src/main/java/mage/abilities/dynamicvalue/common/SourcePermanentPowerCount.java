package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class SourcePermanentPowerCount implements DynamicValue {

    private final boolean allowNegativeValues;
    private String sourcePossessiveForm = "{this}'s"; // for text generation

    public SourcePermanentPowerCount() {
        this(true);
    }

    public SourcePermanentPowerCount(boolean allowNegativeValues) {
        super();
        this.allowNegativeValues = allowNegativeValues;
    }

    protected SourcePermanentPowerCount(final SourcePermanentPowerCount dynamicValue) {
        super();
        this.allowNegativeValues = dynamicValue.allowNegativeValues;
        this.sourcePossessiveForm = dynamicValue.sourcePossessiveForm;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getSourceId());
        if (sourcePermanent == null
                || (sourceAbility.getSourceObjectZoneChangeCounter() > 0
                && sourcePermanent.getZoneChangeCounter(game) > sourceAbility.getSourceObjectZoneChangeCounter())) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null
                && (allowNegativeValues || sourcePermanent.getPower().getValue() >= 0)) {
            return sourcePermanent.getPower().getValue();
        }
        return 0;
    }

    @Override
    public SourcePermanentPowerCount copy() {
        return new SourcePermanentPowerCount(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    public SourcePermanentPowerCount setSourcePossessiveForm(String possessiveForm) {
        this.sourcePossessiveForm = possessiveForm;
        return this;
    }

    @Override
    public String getMessage() {
        return sourcePossessiveForm + " power";
    }
}
