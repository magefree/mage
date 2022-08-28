package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author weirddan455
 */
public final class PrayerOfBinding extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public PrayerOfBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Prayer of Binding enters the battlefield, exile up to one target nonland permanent an opponent controls until Prayer of Binding leaves the battlefield. You gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect("nonland permanent")
                .setText("exile up to one target nonland permanent an opponent controls until {this} leaves the battlefield"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetNonlandPermanent(0, 1, filter, false));
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
