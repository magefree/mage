package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrenkoTinStreetKingpin extends CardImpl {

    public KrenkoTinStreetKingpin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Krenko, Tin Street Kingpin attacks, put a +1/+1 counter on it, then create a number of 1/1 red Goblin creature tokens equal to Krenko's power.
        this.addAbility(new AttacksTriggeredAbility(new KrenkoTinStreetKingpinEffect(), false));
    }

    private KrenkoTinStreetKingpin(final KrenkoTinStreetKingpin card) {
        super(card);
    }

    @Override
    public KrenkoTinStreetKingpin copy() {
        return new KrenkoTinStreetKingpin(this);
    }
}

class KrenkoTinStreetKingpinEffect extends OneShotEffect {

    KrenkoTinStreetKingpinEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on it, " +
                "then create a number of 1/1 red Goblin creature tokens equal to {this}'s power.";
    }

    private KrenkoTinStreetKingpinEffect(final KrenkoTinStreetKingpinEffect effect) {
        super(effect);
    }

    @Override
    public KrenkoTinStreetKingpinEffect copy() {
        return new KrenkoTinStreetKingpinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
        game.getState().processAction(game);
        int xValue = permanent.getPower().getValue();
        return new CreateTokenEffect(new GoblinToken(), xValue).apply(game, source);
    }
}