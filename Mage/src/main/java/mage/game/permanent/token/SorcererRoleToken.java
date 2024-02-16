package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class SorcererRoleToken extends TokenImpl {

    public SorcererRoleToken() {
        super("Sorcerer", "Sorcerer Role token");
        cardType.add(CardType.ENCHANTMENT);
        subtype.add(SubType.AURA);
        subtype.add(SubType.ROLE);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has "Whenever this creature attacks, scry 1."
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new ScryEffect(1, false))
                        .setTriggerPhrase("Whenever this creature attacks, "),
                AttachmentType.AURA
        ).setText("and has \"Whenever this creature attacks, scry 1.\""));
        this.addAbility(ability);
    }

    private SorcererRoleToken(final SorcererRoleToken token) {
        super(token);
    }

    public SorcererRoleToken copy() {
        return new SorcererRoleToken(this);
    }
}
