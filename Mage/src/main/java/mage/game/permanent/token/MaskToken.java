package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TotemArmorAbility;
import mage.constants.Outcome;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class MaskToken extends TokenImpl {

    public MaskToken() {
        super(
                "Mask", "white Aura enchantment token named Mask "
                + "attached to another target permanent. "
                + "The token has enchant permanent and totem armor."
        );
        cardType.add(CardType.ENCHANTMENT);
        color.setWhite(true);
        subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetPermanent();
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        this.addAbility(new TotemArmorAbility());
    }

    public MaskToken(final MaskToken token) {
        super(token);
    }

    public MaskToken copy() {
        return new MaskToken(this);
    }
}
