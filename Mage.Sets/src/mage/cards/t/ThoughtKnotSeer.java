
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class ThoughtKnotSeer extends CardImpl {

    public ThoughtKnotSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{C}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Thought-Knot Seer enters the battlefield, target opponent reveals their hand. You choose a nonland card from it and exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileCardYouChooseTargetOpponentEffect(StaticFilters.FILTER_CARD_A_NON_LAND), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When Thought-Knot Seer leaves the battlefield, target opponent draws a card.
        ability = new LeavesBattlefieldTriggeredAbility(new DrawCardTargetEffect(1), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ThoughtKnotSeer(final ThoughtKnotSeer card) {
        super(card);
    }

    @Override
    public ThoughtKnotSeer copy() {
        return new ThoughtKnotSeer(this);
    }
}
