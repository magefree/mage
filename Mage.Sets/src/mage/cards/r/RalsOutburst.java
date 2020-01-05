package mage.cards.r;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RalsOutburst extends CardImpl {

    public RalsOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{R}");

        // Ral's Outburst deals 3 damage to any target. Look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(2), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false
        ));
    }

    private RalsOutburst(final RalsOutburst card) {
        super(card);
    }

    @Override
    public RalsOutburst copy() {
        return new RalsOutburst(this);
    }
}
