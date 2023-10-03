package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class BogardanPhoenix extends CardImpl {

    public BogardanPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Bogardan Phoenix dies, exile it if it had a death counter on it. Otherwise, return it to the battlefield under your control and put a death counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(new BogardanPhoenixEffect(), false));
    }

    private BogardanPhoenix(final BogardanPhoenix card) {
        super(card);
    }

    @Override
    public BogardanPhoenix copy() {
        return new BogardanPhoenix(this);
    }
}

class BogardanPhoenixEffect extends OneShotEffect {

    public BogardanPhoenixEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it if it had a death counter on it. "
                + "Otherwise, return it to the battlefield under your control "
                + "and put a death counter on it.";
    }

    private BogardanPhoenixEffect(final BogardanPhoenixEffect effect) {
        super(effect);
    }

    @Override
    public BogardanPhoenixEffect copy() {
        return new BogardanPhoenixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent == null
                || controller == null
                || permanent.getZoneChangeCounter(game) + 1
                != source.getSourceObjectZoneChangeCounter()) {
            return false;
        }
        Card card = game.getCard(permanent.getId());
        if (card == null || card.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter()) {
            return false;
        }
        if (permanent.getCounters(game).containsKey(CounterType.DEATH)) {
            return controller.moveCards(card, Zone.EXILED, source, game);
        } else {
            Counters countersToAdd = new Counters();
            countersToAdd.addCounter(CounterType.DEATH.createInstance());
            game.setEnterWithCounters(source.getSourceId(), countersToAdd);
            return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
    }
}
