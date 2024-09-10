package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class PrayerOfBinding extends CardImpl {

    public PrayerOfBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Prayer of Binding enters the battlefield, exile up to one target nonland permanent an opponent controls until Prayer of Binding leaves the battlefield. You gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private PrayerOfBinding(final PrayerOfBinding card) {
        super(card);
    }

    @Override
    public PrayerOfBinding copy() {
        return new PrayerOfBinding(this);
    }
}
