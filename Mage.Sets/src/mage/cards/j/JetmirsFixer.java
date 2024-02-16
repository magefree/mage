package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JetmirsFixer extends CardImpl {

    public JetmirsFixer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}{G}: Jetmir's Fixer gets +1/+1 until end of turn. If mana from a Treasure was spent to activate this ability, put a +1/+1 counter on Jetmir's Fixer instead.
        this.addAbility(new SimpleActivatedAbility(new JetmirsFixerEffect(), new ManaCostsImpl<>("{R}{G}")));
    }

    private JetmirsFixer(final JetmirsFixer card) {
        super(card);
    }

    @Override
    public JetmirsFixer copy() {
        return new JetmirsFixer(this);
    }
}

class JetmirsFixerEffect extends OneShotEffect {

    JetmirsFixerEffect() {
        super(Outcome.Benefit);
        staticText = "{this} gets +1/+1 until end of turn. If mana from a Treasure " +
                "was spent to activate this ability, put a +1/+1 counter on {this} instead";
    }

    private JetmirsFixerEffect(final JetmirsFixerEffect effect) {
        super(effect);
    }

    @Override
    public JetmirsFixerEffect copy() {
        return new JetmirsFixerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (ManaPaidSourceWatcher.getTreasurePaid(source.getId(), game) < 1) {
            game.addEffect(new BoostSourceEffect(1, 1, Duration.EndOfTurn), source);
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
    }
}
