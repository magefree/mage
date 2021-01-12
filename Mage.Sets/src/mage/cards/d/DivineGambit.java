package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author weirddan455
 */
public final class DivineGambit extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact, creature, or enchantment an opponent controls");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DivineGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}");

        // Exile target artifact, creature, or enchantment an opponent controls.
        // That player may put a permanent card from their hand onto the battlefield.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new DivineGambitEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private DivineGambit(final DivineGambit card) {
        super(card);
    }

    @Override
    public DivineGambit copy() {
        return new DivineGambit(this);
    }
}

class DivineGambitEffect extends OneShotEffect {

    public DivineGambitEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "That player may put a permanent card from their hand onto the battlefield";
    }

    private DivineGambitEffect(final DivineGambitEffect effect) {
        super(effect);
    }

    @Override
    public DivineGambitEffect copy() {
        return new DivineGambitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                if (player.chooseUse(outcome, "Put a permanent card from your hand onto the battlefield?", source, game)) {
                    TargetCardInHand target = new TargetCardInHand(StaticFilters.FILTER_CARD_PERMANENT);
                    if (player.chooseTarget(outcome, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            return player.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }
                    }
                }
            }
        }
        return false;
    }
}
