package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class EnchantedAttachedCount implements DynamicValue {

    private final int multiplier;
    private final Collection<SubType> subTypes;

    public EnchantedAttachedCount(SubType... subTypes) {
        this(1, subTypes);
    }

    public EnchantedAttachedCount(int multiplier, SubType... subTypes) {
        this.multiplier = multiplier;
        this.subTypes = Arrays.asList(subTypes);
    }

    protected EnchantedAttachedCount(final EnchantedAttachedCount dynamicValue) {
        this.multiplier = dynamicValue.multiplier;
        this.subTypes = dynamicValue.subTypes;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return 0;
        }

        Permanent permanent = game.getPermanent(sourcePermanent.getAttachedTo());
        return permanent == null ? 0 : permanent
                .getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .map(p -> subTypes.stream().anyMatch(p.getSubtype()::contains))
                .mapToInt(b -> b ? 1 : 0)
                .sum() * multiplier;
    }

    @Override
    public EnchantedAttachedCount copy() {
        return new EnchantedAttachedCount(this);
    }

    @Override
    public String getMessage() {
        return "attached to enchanted creature";
    }
}
