package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
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
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CuriousColossus extends CardImpl {

    public CuriousColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When this creature enters, each creature target opponent controls loses all abilities, becomes a Coward in addition to its other types, and has base power and toughness 1/1.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CuriousColossusEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private CuriousColossus(final CuriousColossus card) {
        super(card);
    }

    @Override
    public CuriousColossus copy() {
        return new CuriousColossus(this);
    }
}

class CuriousColossusEffect extends OneShotEffect {

    CuriousColossusEffect() {
        super(Outcome.Benefit);
        staticText = "each creature target opponent controls loses all abilities, " +
                "becomes a Coward in addition to its other types, and has base power and toughness 1/1";
    }

    private CuriousColossusEffect(final CuriousColossusEffect effect) {
        super(effect);
    }

    @Override
    public CuriousColossusEffect copy() {
        return new CuriousColossusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game
        );
        if (permanents.isEmpty()) {
            return false;
        }
        game.addEffect(new LoseAllAbilitiesTargetEffect(Duration.Custom)
                .setTargetPointer(new FixedTargets(permanents, game)), source);
        game.addEffect(new AddCardSubTypeTargetEffect(SubType.COWARD, Duration.Custom)
                .setTargetPointer(new FixedTargets(permanents, game)), source);
        game.addEffect(new SetBasePowerToughnessTargetEffect(1, 1, Duration.Custom)
                .setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}
