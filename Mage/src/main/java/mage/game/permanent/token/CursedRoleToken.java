package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class CursedRoleToken extends TokenImpl {

    public CursedRoleToken() {
        super("Cursed", "Cursed Role token");
        cardType.add(CardType.ENCHANTMENT);
        subtype.add(SubType.AURA);
        subtype.add(SubType.ROLE);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        // Enchanted creature is 1/1.
        this.addAbility(new SimpleStaticAbility(new SetBasePowerToughnessEnchantedEffect(1, 1)));
    }

    private CursedRoleToken(final CursedRoleToken token) {
        super(token);
    }

    public CursedRoleToken copy() {
        return new CursedRoleToken(this);
    }
}
