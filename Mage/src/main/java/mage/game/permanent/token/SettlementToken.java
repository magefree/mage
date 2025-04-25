package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 * @author TheElk801
 */
public final class SettlementToken extends TokenImpl {

    public SettlementToken() {
        super("Settlement", "Settlement token");
        cardType.add(CardType.ENCHANTMENT);
        color.setGreen(true);
        subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetLandPermanent();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(ability);

        // Enchanted land has "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new AnyColorManaAbility(), AttachmentType.AURA, Duration.WhileOnBattlefield,
                "enchanted land has \"{T}: Add one mana of any color.\""
        )));
    }

    private SettlementToken(final SettlementToken token) {
        super(token);
    }

    public SettlementToken copy() {
        return new SettlementToken(this);
    }
}
