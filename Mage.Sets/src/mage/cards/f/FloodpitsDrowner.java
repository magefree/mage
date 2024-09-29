package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.Ownerable;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FloodpitsDrowner extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with a stun counter on it");

    static {
        filter.add(CounterType.STUN.getPredicate());
    }

    public FloodpitsDrowner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Floodpits Drowner enters, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // {1}{U}, {T}: Shuffle Floodpits Drowner and target creature with a stun counter on it into their owners' libraries.
        ability = new SimpleActivatedAbility(new FloodpitsDrownerEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private FloodpitsDrowner(final FloodpitsDrowner card) {
        super(card);
    }

    @Override
    public FloodpitsDrowner copy() {
        return new FloodpitsDrowner(this);
    }
}

class FloodpitsDrownerEffect extends OneShotEffect {

    FloodpitsDrownerEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle {this} and target creature with a stun counter on it into their owners' libraries";
    }

    private FloodpitsDrownerEffect(final FloodpitsDrownerEffect effect) {
        super(effect);
    }

    @Override
    public FloodpitsDrownerEffect copy() {
        return new FloodpitsDrownerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = new ArrayList<>();
        permanents.add(source.getSourcePermanentIfItStillExists(game));
        permanents.add(game.getPermanent(getTargetPointer().getFirst(game, source)));
        permanents.removeIf(Objects::isNull);
        switch (permanents.size()) {
            case 0:
                return false;
            case 2:
                if (permanents
                        .stream()
                        .map(Ownerable::getOwnerId)
                        .distinct()
                        .count() > 1) {
                    break;
                }
            case 1:
                Player player = game.getPlayer(permanents.get(0).getOwnerId());
                return player != null && player.shuffleCardsToLibrary(new CardsImpl(permanents), game, source);
        }
        for (Permanent permanent : permanents) {
            Player player = game.getPlayer(permanent.getOwnerId());
            if (player != null) {
                player.shuffleCardsToLibrary(permanent, game, source);
            }
        }
        return true;
    }
}
