package mage.cards.l;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class LiminalHold extends CardImpl {

    public LiminalHold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When this enchantment enters, exile up to one target nonland permanent an opponent controls until this enchantment leaves the battlefield. You gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);
    }

    private LiminalHold(final LiminalHold card) {
        super(card);
    }

    @Override
    public LiminalHold copy() {
        return new LiminalHold(this);
    }
}
