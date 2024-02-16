package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PactOfTheSerpent extends CardImpl {

    public PactOfTheSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Choose a creature type. Target player draws X cards and loses X life, where X is the number of creature they control of the chosen type.
        this.getSpellAbility().addEffect(new ChooseCreatureTypeEffect(Outcome.Neutral));
        this.getSpellAbility().addEffect(new PactOfTheSerpentEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private PactOfTheSerpent(final PactOfTheSerpent card) {
        super(card);
    }

    @Override
    public PactOfTheSerpent copy() {
        return new PactOfTheSerpent(this);
    }
}

class PactOfTheSerpentEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    PactOfTheSerpentEffect() {
        super(Outcome.Benefit);
        staticText = "Target player draws X cards and loses X life, " +
                "where X is the number of creatures they control of the chosen type";
    }

    private PactOfTheSerpentEffect(final PactOfTheSerpentEffect effect) {
        super(effect);
    }

    @Override
    public PactOfTheSerpentEffect copy() {
        return new PactOfTheSerpentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        int permCount = game.getBattlefield().count(filter, player.getId(), source, game);
        if (permCount > 0) {
            player.drawCards(permCount, source, game);
            player.loseLife(permCount, game, source, false);
        }
        return true;
    }
}
