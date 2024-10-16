package mage.cards.d;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardWithSameNameAsPermanents;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author noahg
 */
public final class Dichotomancy extends CardImpl {

    public Dichotomancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{U}{U}");

        // For each tapped nonland permanent target opponent controls, search that player’s library for a card with the same name as that permanent and put it onto the battlefield under your control. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new DichotomancyEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Suspend 3-{1}{U}{U}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{1}{U}{U}"), this));
    }

    private Dichotomancy(final Dichotomancy card) {
        super(card);
    }

    @Override
    public Dichotomancy copy() {
        return new Dichotomancy(this);
    }
}

class DichotomancyEffect extends OneShotEffect {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TappedPredicate.TAPPED);
    }

    DichotomancyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "For each tapped nonland permanent target opponent controls, " +
                "search that player's library for a card with the same name as that permanent. " +
                "Put those cards onto the battlefield under your control, then that player shuffles.";
    }

    private DichotomancyEffect(DichotomancyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        Set<UUID> set = game
                .getBattlefield()
                .getActivePermanents(filter, opponent.getId(), source, game)
                .stream()
                .map(MageItem::getId)
                .collect(Collectors.toSet());
        TargetCardInLibrary target = new TargetCardWithSameNameAsPermanents(set);
        controller.searchLibrary(target, source, game, opponent.getId());
        Cards cards = new CardsImpl();
        for (UUID targetId : target.getTargets()) {
            cards.add(opponent.getLibrary().getCard(targetId, game));
        }
        controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
        opponent.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public DichotomancyEffect copy() {
        return new DichotomancyEffect(this);
    }
}
