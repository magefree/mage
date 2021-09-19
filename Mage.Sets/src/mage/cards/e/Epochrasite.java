package mage.cards.e;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Epochrasite extends CardImpl {

    public Epochrasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Epochrasite enters the battlefield with three +1/+1 counters on it if you didn't cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                        new InvertCondition(CastFromHandSourcePermanentCondition.instance),
                        "{this} enters the battlefield with three +1/+1 counters on it if you didn't cast it from your hand", ""),
                new CastFromHandWatcher());

        // When Epochrasite dies, exile it with three time counters on it and it gains suspend.
        this.addAbility(new DiesSourceTriggeredAbility(new EpochrasiteEffect()));
    }

    private Epochrasite(final Epochrasite card) {
        super(card);
    }

    @Override
    public Epochrasite copy() {
        return new Epochrasite(this);
    }
}

class EpochrasiteEffect extends OneShotEffect {

    public EpochrasiteEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it with three time counters on it and it gains suspend";
    }

    public EpochrasiteEffect(final EpochrasiteEffect effect) {
        super(effect);
    }

    @Override
    public EpochrasiteEffect copy() {
        return new EpochrasiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller == null || card == null) {
            return false;
        }
        card = card.getMainCard();

        if (game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }

        UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
        controller.moveCardToExileWithInfo(card, exileId, "Suspended cards of " + controller.getName(), source, game, Zone.GRAVEYARD, true);
        card.addCounters(CounterType.TIME.createInstance(3), source.getControllerId(), source, game);
        game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);

        return true;
    }
}
