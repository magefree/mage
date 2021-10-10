package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.HumanToken;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlaughterSpecialist extends CardImpl {

    public SlaughterSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Slaughter Specialist enters the battlefield, each opponent creates a 1/1 white Human creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SlaughterSpecialistEffect()));

        // Whenever a creature an opponent controls dies, put a +1/+1 counter on Slaughter Specialist.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));
    }

    private SlaughterSpecialist(final SlaughterSpecialist card) {
        super(card);
    }

    @Override
    public SlaughterSpecialist copy() {
        return new SlaughterSpecialist(this);
    }
}

class SlaughterSpecialistEffect extends OneShotEffect {

    SlaughterSpecialistEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent creates a 1/1 white Human creature token";
    }

    private SlaughterSpecialistEffect(final SlaughterSpecialistEffect effect) {
        super(effect);
    }

    @Override
    public SlaughterSpecialistEffect copy() {
        return new SlaughterSpecialistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new HumanToken();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            token.putOntoBattlefield(1, game, source, opponentId);
        }
        return true;
    }
}
