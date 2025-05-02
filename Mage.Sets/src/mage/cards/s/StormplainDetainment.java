package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author balazskristof
 */
public final class StormplainDetainment extends CardImpl {

    public StormplainDetainment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When this enchantment enters, exile target nonland permanent an opponent controls until this enchantment leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private StormplainDetainment(final StormplainDetainment card) {
        super(card);
    }

    @Override
    public StormplainDetainment copy() {
        return new StormplainDetainment(this);
    }
}
