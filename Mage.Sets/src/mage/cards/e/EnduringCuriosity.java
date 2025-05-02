package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EnduringGlimmerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringCuriosity extends CardImpl {

    public EnduringCuriosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever a creature you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true
        ));

        // When Enduring Curiosity dies, if it was a creature, return it to the battlefield under its owner's control. It's an enchantment.
        this.addAbility(new EnduringGlimmerTriggeredAbility());
    }

    private EnduringCuriosity(final EnduringCuriosity card) {
        super(card);
    }

    @Override
    public EnduringCuriosity copy() {
        return new EnduringCuriosity(this);
    }
}
