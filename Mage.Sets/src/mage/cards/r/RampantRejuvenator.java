package mage.cards.r;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampantRejuvenator extends CardImpl {

    public RampantRejuvenator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Rampant Rejuvenator enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(2)
        ), "with two +1/+1 counters on it"));

        // When Rampant Rejuvenator dies, search your library for up to X basic land cards, where X is Rampant Rejuvenator's power, put them onto the battlefield, then shuffle.
        this.addAbility(new DiesSourceTriggeredAbility(new RampantRejuvenatorEffect()));
    }

    private RampantRejuvenator(final RampantRejuvenator card) {
        super(card);
    }

    @Override
    public RampantRejuvenator copy() {
        return new RampantRejuvenator(this);
    }
}

class RampantRejuvenatorEffect extends OneShotEffect {

    RampantRejuvenatorEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to X basic land cards, " +
                "where X is {this}'s power, put them onto the battlefield, then shuffle";
    }

    private RampantRejuvenatorEffect(final RampantRejuvenatorEffect effect) {
        super(effect);
    }

    @Override
    public RampantRejuvenatorEffect copy() {
        return new RampantRejuvenatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(
                0, permanent.getPower().getValue(),
                StaticFilters.FILTER_CARD_BASIC_LANDS
        );
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        player.getLibrary()
                .getCards(game)
                .stream()
                .map(MageItem::getId)
                .filter(target.getTargets()::contains)
                .forEach(cards::add);
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
