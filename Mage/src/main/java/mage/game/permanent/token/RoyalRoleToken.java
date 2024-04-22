package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.WardAbility;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class RoyalRoleToken extends TokenImpl {

    public RoyalRoleToken() {
        super("Royal", "Royal Role token");
        cardType.add(CardType.ENCHANTMENT);
        subtype.add(SubType.AURA);
        subtype.add(SubType.ROLE);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has ward {1}
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(1), false), AttachmentType.AURA
        ).setText("and has ward {1}"));
        this.addAbility(ability);
    }

    private RoyalRoleToken(final RoyalRoleToken token) {
        super(token);
    }

    public RoyalRoleToken copy() {
        return new RoyalRoleToken(this);
    }
}
