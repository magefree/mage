package mage.util.functions;

import mage.abilities.effects.common.cost.MiracleCostModifier;

import java.io.Serializable;
import java.util.function.Supplier;

public interface MiracleCostModifierCreator extends Supplier<MiracleCostModifier>, Serializable { }
