package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class SplitTheParty extends CardImpl {

    public SplitTheParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Choose target player. Return half the creatures they control to their owner's hand, rounded up.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new SplitThePartyEffect());
    }

    private SplitTheParty(final SplitTheParty card) {
        super(card);
    }

    @Override
    public SplitTheParty copy() {
        return new SplitTheParty(this);
    }
}

class SplitThePartyEffect extends OneShotEffect {

    public SplitThePartyEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Choose target player. Return half the creatures they control to their owner's hand, rounded up";
    }

    private SplitThePartyEffect(final SplitThePartyEffect effect) {
        super(effect);
    }

    @Override
    public SplitThePartyEffect copy() {
        return new SplitThePartyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || targetPlayer == null) {
            return false;
        }
        int numCreatures = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game);
        if (numCreatures > 0) {
            int halfCreatures = (numCreatures / 2) + (numCreatures % 2);
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures controlled by " + targetPlayer.getName());
            filter.add(new ControllerIdPredicate(targetPlayer.getId()));
            TargetCreaturePermanent target = new TargetCreaturePermanent(halfCreatures, halfCreatures, filter, true);
            if (controller.chooseTarget(outcome, target, source, game)) {
                Set<Card> cardsToHand = new HashSet<>();
                for (UUID creatureId : target.getTargets()) {
                    Card card = game.getPermanent(creatureId);
                    if (card != null) {
                        cardsToHand.add(card);
                    }
                }
                controller.moveCards(cardsToHand, Zone.HAND, source, game);
            }
        }
        return true;
    }
}
