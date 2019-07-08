package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

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
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl("{1}{U}{U}"), this));
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
        filter.add(TappedPredicate.instance);
    }

    DichotomancyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "For each tapped nonland permanent target opponent controls, " +
                "search that player's library for a card with the same name as that permanent. " +
                "Put those cards onto the battlefield under your control, then that player shuffles their library.";
    }

    private DichotomancyEffect(DichotomancyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && opponent != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, opponent.getId(), game)) {
                String name = permanent.getName();
                FilterCard filterCard = new FilterCard("card named \"" + name + '"');
                filterCard.add(new NamePredicate(name));
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filterCard);
                if (controller.searchLibrary(target, source, game, opponent.getId())) {
                    controller.moveCards(opponent.getLibrary().getCard(target.getFirstTarget(), game), Zone.BATTLEFIELD, source, game);
                }
            }
            opponent.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public DichotomancyEffect copy() {
        return new DichotomancyEffect(this);
    }
}