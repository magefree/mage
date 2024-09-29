package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EnduringGlimmerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringVitality extends CardImpl {

    public EnduringVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.ELK);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Creatures you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // When Enduring Vitality dies, if it was a creature, return it to the battlefield under its owner's control. It's an enchantment.
        this.addAbility(new EnduringGlimmerTriggeredAbility());
    }

    private EnduringVitality(final EnduringVitality card) {
        super(card);
    }

    @Override
    public EnduringVitality copy() {
        return new EnduringVitality(this);
    }
}
