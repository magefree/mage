package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BanishToAnotherUniverse extends CardImpl {

    public BanishToAnotherUniverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // Affinity for historic permanents
        this.addAbility(new AffinityAbility(AffinityType.HISTORIC));

        // When Banish to Another Universe enters the battlefield, exile target nonland permanent an opponent controls until Banish to Another Universe leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private BanishToAnotherUniverse(final BanishToAnotherUniverse card) {
        super(card);
    }

    @Override
    public BanishToAnotherUniverse copy() {
        return new BanishToAnotherUniverse(this);
    }
}
