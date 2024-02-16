package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyrkulsEdict extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent(
            "a creature with the greatest power among creatures that player controls"
    );

    static {
        filter.add(GreatestPowerControlledPredicate.instance);
    }

    public MyrkulsEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();

        // 1-9 | Choose an opponent. That player sacrifices a creature.
        effect.addTableEntry(1, 9, new MyrkulsEdictEffect());

        // 10-19 | Each opponent sacrifices a creature.
        effect.addTableEntry(10, 19, new SacrificeOpponentsEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // 20 | Each opponent sacrifices a creature with the greatest power among creatures that player controls.
        effect.addTableEntry(20, 20, new SacrificeOpponentsEffect(filter));

        this.getSpellAbility().addEffect(effect);
    }

    private MyrkulsEdict(final MyrkulsEdict card) {
        super(card);
    }

    @Override
    public MyrkulsEdict copy() {
        return new MyrkulsEdict(this);
    }
}

class MyrkulsEdictEffect extends OneShotEffect {

    MyrkulsEdictEffect() {
        super(Outcome.Benefit);
        staticText = "choose an opponent. That player sacrifices a creature";
    }

    private MyrkulsEdictEffect(final MyrkulsEdictEffect effect) {
        super(effect);
    }

    @Override
    public MyrkulsEdictEffect copy() {
        return new MyrkulsEdictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetOpponent target = new TargetOpponent(true);
        if (!target.choose(outcome, source.getControllerId(), source.getId(), source, game)) {
            return false;
        }

        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            return false;
        }

        Effect sacrificeEffect = new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "");
        sacrificeEffect.setTargetPointer(new FixedTarget(opponent.getId()));

        return sacrificeEffect.apply(game, source);
    }
}
