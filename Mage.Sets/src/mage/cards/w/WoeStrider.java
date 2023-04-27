package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GoatToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WoeStrider extends CardImpl {

    public WoeStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Woe Strider enters the battlefield, create a 0/1 white Goat creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoatToken())));

        // Sacrifice another creature: Scry 1.
        this.addAbility(new SimpleActivatedAbility(
                new ScryEffect(1, false), new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE))
        ));

        // Escape—{3}{B}{B}, Exile four other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{B}{B}", 4));

        // Woe Strider escapes with two +1/+1 counters on it.
        this.addAbility(new EscapesWithAbility(2));
    }

    private WoeStrider(final WoeStrider card) {
        super(card);
    }

    @Override
    public WoeStrider copy() {
        return new WoeStrider(this);
    }
}
