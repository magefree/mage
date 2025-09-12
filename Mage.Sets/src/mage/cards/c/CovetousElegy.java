package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovetousElegy extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
    private static final Hint hint = new ValueHint("Creatures your opponents control", xValue);

    public CovetousElegy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{B}");

        // Each player chooses up to two creatures they control, then sacrifices the rest. Then you create a tapped Treasure token for each creature your opponents control.
        this.getSpellAbility().addEffect(new CovetousElegyEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken(), xValue).concatBy("Then"));
        this.getSpellAbility().addHint(hint);
    }

    private CovetousElegy(final CovetousElegy card) {
        super(card);
    }

    @Override
    public CovetousElegy copy() {
        return new CovetousElegy(this);
    }
}

class CovetousElegyEffect extends OneShotEffect {

    CovetousElegyEffect() {
        super(Outcome.Benefit);
        staticText = "each player chooses up to two creatures they control, then sacrifices the rest";
    }

    private CovetousElegyEffect(final CovetousElegyEffect effect) {
        super(effect);
    }

    @Override
    public CovetousElegyEffect copy() {
        return new CovetousElegyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> creatures = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetPermanent target = new TargetControlledCreaturePermanent(0, 2);
            target.withNotTarget(true);
            target.withChooseHint("the rest will be sacrificed");
            player.choose(outcome, target, source, game);
            creatures.addAll(target.getTargets());
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game
        )) {
            if (!creatures.contains(permanent.getId())) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
