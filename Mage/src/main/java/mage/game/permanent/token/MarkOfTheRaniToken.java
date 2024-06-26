package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author grimreap124
 */
public final class MarkOfTheRaniToken extends TokenImpl {

    public MarkOfTheRaniToken() {
        super(
                "Mark of the Rani",
                "red Aura enchantment token named Mark of the Rani attached to another target creature. That token has enchant creature and \"Enchanted creature gets +2/+2 and is goaded.\"");
        cardType.add(CardType.ENCHANTMENT);
        color.setRed(true);
        subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 and is goaded.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new GoadAttachedEffect());
        this.addAbility(ability);
    }

    private MarkOfTheRaniToken(final MarkOfTheRaniToken token) {
        super(token);
    }

    public MarkOfTheRaniToken copy() {
        return new MarkOfTheRaniToken(this);
    }
}