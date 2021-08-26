package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class Poultrygeist extends CardImpl {

    public Poultrygeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature dies, you may roll a six-sided die. If you roll a 1, sacrifice Poultrygeist. Otherwise, put a +1/+1 counter on Poultrygeist.
        Ability ability = new DiesCreatureTriggeredAbility(new PoultrygeistEffect(), true);
        this.addAbility(ability);
    }

    private Poultrygeist(final Poultrygeist card) {
        super(card);
    }

    @Override
    public Poultrygeist copy() {
        return new Poultrygeist(this);
    }
}

class PoultrygeistEffect extends OneShotEffect {

    PoultrygeistEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "roll a six-sided die. If you roll a 1, sacrifice {this}. Otherwise, put a +1/+1 counter on {this}";
    }

    PoultrygeistEffect(final PoultrygeistEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int result = controller.rollDice(outcome, source, game, 6);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                if (result == 1) {
                    return permanent.sacrifice(source, game);
                } else {
                    return new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
                }
            }
        }
        return false;
    }

    @Override
    public PoultrygeistEffect copy() {
        return new PoultrygeistEffect(this);
    }
}
