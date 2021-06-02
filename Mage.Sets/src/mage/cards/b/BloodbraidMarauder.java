package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.CascadeAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author weirddan455
 */
public final class BloodbraidMarauder extends CardImpl {

    private static final String REMINDER_TEXT = " <i>(When you cast this spell, "
            + "exile cards from the top of your library until you exile a "
            + "nonland card whose mana value is less than this spell's mana value. "
            + "You may cast that spell without paying its mana cost "
            + "if its mana value is less than this spell's mana value. "
            + "Then put all cards exiled this way that weren't cast on the bottom of your library in a random order.)</i>";

    public BloodbraidMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Bloodbraid Marauder can't block.
        this.addAbility(new CantBlockAbility());

        // Delirium — This spell has cascade as long as there are four or more card types among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new CascadeAbility(), Duration.WhileOnStack, true),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; This spell has cascade as long as there are four or more card types among cards in your graveyard." + REMINDER_TEXT
        )).addHint(CardTypesInGraveyardHint.YOU));
    }

    private BloodbraidMarauder(final BloodbraidMarauder card) {
        super(card);
    }

    @Override
    public BloodbraidMarauder copy() {
        return new BloodbraidMarauder(this);
    }
}
