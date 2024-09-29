package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.AfterlifeAbility;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IndebtedSpirit extends CardImpl {

    public IndebtedSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {2}{W}
        this.addAbility(new BestowAbility(this, "{2}{W}"));

        // Afterlife 1
        this.addAbility(new AfterlifeAbility(1));

        // Enchanted creature gets +1/+1 and has afterlife 1.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new AfterlifeAbility(1), AttachmentType.AURA
        ).setText("and has afterlife 1"));
        this.addAbility(ability);
    }

    private IndebtedSpirit(final IndebtedSpirit card) {
        super(card);
    }

    @Override
    public IndebtedSpirit copy() {
        return new IndebtedSpirit(this);
    }
}
