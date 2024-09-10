package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RushOfDread extends CardImpl {

    public RushOfDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1} -- Target opponent sacrifices half the creatures they control, rounded up.
        this.getSpellAbility().addEffect(new RushOfDreadSacrificeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {2} -- Target opponent discards half the cards in their hand, rounded up.
        this.getSpellAbility().addMode(new Mode(new RushOfDreadDiscardEffect())
                .addTarget(new TargetOpponent())
                .withCost(new GenericManaCost(2)));

        // + {2} -- Target opponent loses half their life, rounded up.
        this.getSpellAbility().addMode(new Mode(new LoseHalfLifeTargetEffect())
                .addTarget(new TargetOpponent())
                .withCost(new GenericManaCost(2)));
    }

    private RushOfDread(final RushOfDread card) {
        super(card);
    }

    @Override
    public RushOfDread copy() {
        return new RushOfDread(this);
    }
}

class RushOfDreadSacrificeEffect extends OneShotEffect {

    RushOfDreadSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent sacrifices half the creatures they control, rounded up";
    }

    private RushOfDreadSacrificeEffect(final RushOfDreadSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public RushOfDreadSacrificeEffect copy() {
        return new RushOfDreadSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        int creatures = game
                .getBattlefield()
                .count(StaticFilters.FILTER_CONTROLLED_CREATURES, opponent.getId(), source, game);
        int toSac = Math.floorDiv(creatures, 2) + creatures % 2;
        return new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, toSac, "")
                .setTargetPointer(new FixedTarget(opponent.getId()))
                .apply(game, source);
    }
}

class RushOfDreadDiscardEffect extends OneShotEffect {

    RushOfDreadDiscardEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent discards half the cards in their hand, rounded up";
    }

    private RushOfDreadDiscardEffect(final RushOfDreadDiscardEffect effect) {
        super(effect);
    }

    @Override
    public RushOfDreadDiscardEffect copy() {
        return new RushOfDreadDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        return opponent != null
                && !opponent.getHand().isEmpty()
                && opponent.discard(
                Math.floorDiv(opponent.getHand().size(), 2) +
                        opponent.getHand().size() % 2,
                false, false, source, game
        ).size() > 0;
    }
}
