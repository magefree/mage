package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 * @author muz
 */
public final class FatefulDiscovery extends CardImpl {

    public FatefulDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // Whenever an artifact you control enters, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
            new DrawCardSourceControllerEffect(1),
            StaticFilters.FILTER_PERMANENT_ARTIFACT_AN
        ));
    }

    private FatefulDiscovery(final FatefulDiscovery card) {
        super(card);
    }

    @Override
    public FatefulDiscovery copy() {
        return new FatefulDiscovery(this);
    }
}
