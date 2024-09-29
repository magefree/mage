package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class OrphansOfTheWheat extends CardImpl {

    public OrphansOfTheWheat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Orphans of the Wheat attacks, tap any number of untapped creatures you control. Orphans of the Wheat gets +1/+1 until end of turn for each creature tapped this way.
        this.addAbility(new AttacksTriggeredAbility(new OrphansOfTheWheatEffect()));
    }

    private OrphansOfTheWheat(final OrphansOfTheWheat card) {
        super(card);
    }

    @Override
    public OrphansOfTheWheat copy() {
        return new OrphansOfTheWheat(this);
    }
}

class OrphansOfTheWheatEffect extends OneShotEffect {

    OrphansOfTheWheatEffect() {
        super(Outcome.Benefit);
        staticText = "tap any number of untapped creatures you control. " +
                "{this} gets +1/+1 until end of turn for each creature tapped this way";
    }

    private OrphansOfTheWheatEffect(final OrphansOfTheWheatEffect effect) {
        super(effect);
    }

    @Override
    public OrphansOfTheWheatEffect copy() {
        return new OrphansOfTheWheatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES, true
        );
        player.choose(Outcome.Tap, target, source, game);
        List<Permanent> permanents = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        for (Permanent permanent : permanents) {
            permanent.tap(source, game);
        }
        if (permanents.isEmpty()) {
            return false;
        }
        int amount = permanents.size();
        game.addEffect(new BoostSourceEffect(amount, amount, Duration.EndOfTurn), source);
        return true;
    }
}
