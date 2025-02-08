package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TicketTortoise extends CardImpl {

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS);

    public TicketTortoise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When this creature enters, if an opponent controls more lands than you, you create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())
                .setText("you create a Treasure token")).withInterveningIf(condition));
    }

    private TicketTortoise(final TicketTortoise card) {
        super(card);
    }

    @Override
    public TicketTortoise copy() {
        return new TicketTortoise(this);
    }
}
