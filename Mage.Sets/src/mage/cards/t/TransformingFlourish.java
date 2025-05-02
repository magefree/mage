package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DemonstrateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TransformingFlourish extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature you don't control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public TransformingFlourish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Demonstrate
        this.addAbility(new DemonstrateAbility());

        // Destroy target artifact or creature you don't control. If that permanent is destroyed this way, its controller exiles cards from the top of their library until they exile a nonland card, then they may cast that card without paying its mana cost.
        this.getSpellAbility().addEffect(new TransformingFlourishEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private TransformingFlourish(final TransformingFlourish card) {
        super(card);
    }

    @Override
    public TransformingFlourish copy() {
        return new TransformingFlourish(this);
    }
}

class TransformingFlourishEffect extends OneShotEffect {

    TransformingFlourishEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target artifact or creature you don't control. " +
                "If that permanent is destroyed this way, its controller exiles cards " +
                "from the top of their library until they exile a nonland card, " +
                "then they may cast that card without paying its mana cost";
    }

    private TransformingFlourishEffect(final TransformingFlourishEffect effect) {
        super(effect);
    }

    @Override
    public TransformingFlourishEffect copy() {
        return new TransformingFlourishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.destroy(source, game)) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null || !player.getLibrary().hasCards()) {
            return true;
        }
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            if (!card.isLand(game)) {
                CardUtil.castSpellWithAttributesForFree(player, source, game, card);
                return true;
            }
        }
        return true;
    }
}
