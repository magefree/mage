package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class StaticNet extends CardImpl {

    public StaticNet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When Static Net enters the battlefield, exile target nonland permanent an opponent controls until Static Net leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // When Static Net enters the battlefield, you gain 2 life and create a tapped Powerstone token.
        ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2));
        ability.addEffect(new CreateTokenEffect(new PowerstoneToken(), 1, true).concatBy("and"));
        this.addAbility(ability);
    }

    private StaticNet(final StaticNet card) {
        super(card);
    }

    @Override
    public StaticNet copy() {
        return new StaticNet(this);
    }
}
