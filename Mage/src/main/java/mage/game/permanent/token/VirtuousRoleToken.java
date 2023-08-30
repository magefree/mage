package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class VirtuousRoleToken extends TokenImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT);

    public VirtuousRoleToken() {
        super("Virtuous", "Virtuous Role token");
        cardType.add(CardType.ENCHANTMENT);
        subtype.add(SubType.AURA);
        subtype.add(SubType.ROLE);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 for each enchantment you control.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(xValue, xValue)));
    }

    private VirtuousRoleToken(final VirtuousRoleToken token) {
        super(token);
    }

    public VirtuousRoleToken copy() {
        return new VirtuousRoleToken(this);
    }
}
