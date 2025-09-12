package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderManNoir extends CardImpl {

    public SpiderManNoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever a creature you control attacks alone, put a +1/+1 counter on it. Then surveil X, where X is the number of counters on it.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it")
        );
        ability.addEffect(new SpiderManNoirEffect());
        this.addAbility(ability);
    }

    private SpiderManNoir(final SpiderManNoir card) {
        super(card);
    }

    @Override
    public SpiderManNoir copy() {
        return new SpiderManNoir(this);
    }
}

class SpiderManNoirEffect extends OneShotEffect {

    SpiderManNoirEffect() {
        super(Outcome.Benefit);
        staticText = "Then surveil X, where X is the number of counters on it";
    }

    private SpiderManNoirEffect(final SpiderManNoirEffect effect) {
        super(effect);
    }

    @Override
    public SpiderManNoirEffect copy() {
        return new SpiderManNoirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        return player != null
                && permanent != null
                && player.surveil(permanent.getCounters(game).getTotalCount(), source, game);
    }
}
