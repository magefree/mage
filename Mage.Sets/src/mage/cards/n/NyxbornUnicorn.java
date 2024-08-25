package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NyxbornUnicorn extends CardImpl {

    public NyxbornUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bestow {3}{W}
        this.addAbility(new BestowAbility(this, "{3}{W}"));

        // Mentor
        this.addAbility(new MentorAbility());

        // Enchanted creature gets +2/+2 and has mentor.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MentorAbility(), AttachmentType.AURA
        ).setText("and has mentor"));
        this.addAbility(ability);
    }

    private NyxbornUnicorn(final NyxbornUnicorn card) {
        super(card);
    }

    @Override
    public NyxbornUnicorn copy() {
        return new NyxbornUnicorn(this);
    }
}
