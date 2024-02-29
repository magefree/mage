package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.OozeTrampleToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class PrintlifterOoze extends CardImpl {

    public PrintlifterOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Printlifter Ooze or another creature you control is turned face up, create a 0/0 green Ooze creature token with trample.
        // The token enters the battlefield with X +1/+1 counters on it, where X is the number of other creatures you control.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(
                new PrintlifterOozeEffect(), new FilterControlledCreaturePermanent("{this} or another creature you control")
        ));

        // Disguise {3}{G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{3}{G}")));
    }

    private PrintlifterOoze(final PrintlifterOoze card) {
        super(card);
    }

    @Override
    public PrintlifterOoze copy() {
        return new PrintlifterOoze(this);
    }
}

class PrintlifterOozeEffect extends OneShotEffect {

    PrintlifterOozeEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 0/0 green Ooze creature token with trample. The token enters the battlefield " +
                "with X +1/+1 counters on it, where X is the number of other creatures you control";
    }

    private PrintlifterOozeEffect(final PrintlifterOozeEffect effect) {
        super(effect);
    }

    @Override
    public PrintlifterOozeEffect copy() {
        return new PrintlifterOozeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controller = source.getControllerId();
        if (controller == null) {
            return false;
        }
        int xVal = game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, controller, source, game);
        Effect effect = new CreateTokenEffect(new OozeTrampleToken()).entersWithCounters(CounterType.P1P1, StaticValue.get(xVal));
        return effect.apply(game, source);
    }
}
