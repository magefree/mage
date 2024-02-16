package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class ConclaveTribunal extends CardImpl {

    public ConclaveTribunal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When Conclave Tribunal enters the battlefield, exile target nonland permanent an opponent controls until Conclave Tribunal leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private ConclaveTribunal(final ConclaveTribunal card) {
        super(card);
    }

    @Override
    public ConclaveTribunal copy() {
        return new ConclaveTribunal(this);
    }
}
