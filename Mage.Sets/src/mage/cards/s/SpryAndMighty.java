package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SpryAndMighty extends CardImpl {

    public SpryAndMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Choose two creatures you control. You draw X cards and the chosen creatures get +X/+X and gain trample until end of turn, where X is the difference between the chosen creatures' powers.
        this.getSpellAbility().addEffect(new SpryAndMightyEffect());
    }

    private SpryAndMighty(final SpryAndMighty card) {
        super(card);
    }

    @Override
    public SpryAndMighty copy() {
        return new SpryAndMighty(this);
    }
}

class SpryAndMightyEffect extends OneShotEffect {

    SpryAndMightyEffect() {
        super(Outcome.Benefit);
        staticText = "choose two creatures you control. You draw X cards and the chosen creatures get +X/+X " +
                "and gain trample until end of turn, where X is the difference between the chosen creatures' powers";
    }

    private SpryAndMightyEffect(final SpryAndMightyEffect effect) {
        super(effect);
    }

    @Override
    public SpryAndMightyEffect copy() {
        return new SpryAndMightyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game, 2
        )) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent(2);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        List<Permanent> permanents = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.size() != 2) {
            return false;
        }
        game.informPlayers(
                CardUtil.getSourceLogName(game, source) + ": " +
                        player.getLogName() + " chooses " +
                        permanents.get(0).getLogName() + " and " +
                        permanents.get(1).getLogName()
        );
        int diff = Math.abs(permanents.get(0).getPower().getValue() - permanents.get(1).getPower().getValue());
        if (diff > 0) {
            player.drawCards(diff, source, game);
            game.addEffect(new BoostTargetEffect(diff, diff)
                    .setTargetPointer(new FixedTargets(permanents, game)), source);
        }
        game.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}
