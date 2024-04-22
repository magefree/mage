package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class BorrowedTime extends CardImpl {

    public BorrowedTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Borrowed Time enters the battlefield, exile target nonland permanent an opponent controls until Borrowed Time leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private BorrowedTime(final BorrowedTime card) {
        super(card);
    }

    @Override
    public BorrowedTime copy() {
        return new BorrowedTime(this);
    }
}
