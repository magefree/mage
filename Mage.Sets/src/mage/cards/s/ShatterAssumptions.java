package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShatterAssumptions extends CardImpl {

    public ShatterAssumptions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Choose one —
        // • Target opponent reveals their hand and discards all colorless nonland cards.
        this.getSpellAbility().addEffect(new ShatterAssumptionsEffect(true));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Target opponent reveals their hand and discards all multicolored cards.
        Mode mode = new Mode(new ShatterAssumptionsEffect(false));
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    private ShatterAssumptions(final ShatterAssumptions card) {
        super(card);
    }

    @Override
    public ShatterAssumptions copy() {
        return new ShatterAssumptions(this);
    }
}

class ShatterAssumptionsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterNonlandCard("colorless nonland");
    private static final FilterCard filter2 = new FilterCard("multicolored");

    static {
        filter.add(ColorlessPredicate.instance);
        filter2.add(MulticoloredPredicate.instance);
    }

    private final boolean colorless;

    ShatterAssumptionsEffect(boolean colorless) {
        super(Outcome.Benefit);
        this.colorless = colorless;
        if (colorless) {
            staticText = "Target opponent reveals their hand and discards all colorless nonland cards.";
        } else {
            staticText = "Target opponent reveals their hand and discards all multicolored cards.";
        }
    }

    private ShatterAssumptionsEffect(final ShatterAssumptionsEffect effect) {
        super(effect);
        this.colorless = effect.colorless;
    }

    @Override
    public ShatterAssumptionsEffect copy() {
        return new ShatterAssumptionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        player.revealCards(source, player.getHand(), game);
        FilterCard f;
        if (colorless) {
            f = filter;
        } else {
            f = filter2;
        }
        player.discard(new CardsImpl(player.getHand().getCards(f, game)), false, source, game);
        return true;
    }
}