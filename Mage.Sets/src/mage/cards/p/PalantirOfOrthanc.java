package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PalantirOfOrthanc extends CardImpl {

    public PalantirOfOrthanc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your end step, put an influence counter on Palantir of Orthanc and scry 2. Then target opponent may have you draw a card. If that player doesn't, you mill X cards, where X is the number of influence counters on Palantir of Orthanc, and that player loses life equal to the total mana value of those cards.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.INFLUENCE.createInstance()),
                TargetController.YOU, false
        );
        ability.addEffect(new ScryEffect(2, false).concatBy("and"));
        ability.addEffect(new PalantirOfOrthancEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PalantirOfOrthanc(final PalantirOfOrthanc card) {
        super(card);
    }

    @Override
    public PalantirOfOrthanc copy() {
        return new PalantirOfOrthanc(this);
    }
}

class PalantirOfOrthancEffect extends OneShotEffect {

    PalantirOfOrthancEffect() {
        super(Outcome.Benefit);
        staticText = "Then target opponent may have you draw a card. If that player doesn't, " +
                "you mill X cards, where X is the number of influence counters on {this}, " +
                "and that player loses life equal to the total mana value of those cards.";
    }

    private PalantirOfOrthancEffect(final PalantirOfOrthancEffect effect) {
        super(effect);
    }

    @Override
    public PalantirOfOrthancEffect copy() {
        return new PalantirOfOrthancEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        if (opponent.chooseUse(outcome, "Have " + controller.getName() + " draw a card?", source, game)) {
            return controller.drawCards(1, source, game) > 0;
        }
        int counters = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(permanent -> permanent.getCounters(game))
                .map(c -> c.getCount(CounterType.INFLUENCE))
                .orElse(0);
        if (counters < 1) {
            return false;
        }
        int total = controller
                .millCards(counters, source, game)
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .sum();
        opponent.loseLife(total, game, source, false);
        return true;
    }
}
