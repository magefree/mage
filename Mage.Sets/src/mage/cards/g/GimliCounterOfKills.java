package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GimliCounterOfKills extends CardImpl {

    public GimliCounterOfKills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature an opponent controls dies, Gimli, Counter of Kills deals 1 damage to that creature's controller.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new GimliCounterOfKillsEffect(), false,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));
    }

    private GimliCounterOfKills(final GimliCounterOfKills card) {
        super(card);
    }

    @Override
    public GimliCounterOfKills copy() {
        return new GimliCounterOfKills(this);
    }
}

class GimliCounterOfKillsEffect extends OneShotEffect {

    GimliCounterOfKillsEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to that creature's controller";
    }

    private GimliCounterOfKillsEffect(final GimliCounterOfKillsEffect effect) {
        super(effect);
    }

    @Override
    public GimliCounterOfKillsEffect copy() {
        return new GimliCounterOfKillsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable((Permanent) getValue("creatureDied"))
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> player.damage(1, source, game) > 0)
                .orElse(false);
    }
}
