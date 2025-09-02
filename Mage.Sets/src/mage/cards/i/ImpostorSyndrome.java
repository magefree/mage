package mage.cards.i;

import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImpostorSyndrome extends CardImpl {

    public ImpostorSyndrome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");

        // Whenever a nontoken creature you control deals combat damage to a player, create a token that's a copy of it, except it isn't legendary.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenCopyTargetEffect()
                        .setIsntLegendary(true)
                        .setText("create a token that's a copy of it, except it isn't legendary"),
                StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, false,
                SetTargetPointer.PERMANENT, true
        ));
    }

    private ImpostorSyndrome(final ImpostorSyndrome card) {
        super(card);
    }

    @Override
    public ImpostorSyndrome copy() {
        return new ImpostorSyndrome(this);
    }
}
