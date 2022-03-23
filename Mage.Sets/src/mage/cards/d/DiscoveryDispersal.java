package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiscoveryDispersal extends SplitCard {

    public DiscoveryDispersal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.INSTANT}, "{1}{U/B}", "{3}{U}{B}", SpellAbilityType.SPLIT);

        // Discovery
        // Surveil 2, then draw a card.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new SurveilEffect(2).setText("Surveil 2,")
        );
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new DrawCardSourceControllerEffect(1
                ).setText("then draw a card. <i>(To surveil 2, "
                        + "look at the top two cards of your library, "
                        + "then put any number of them into your graveyard "
                        + "and the rest on top of your library "
                        + "in any order.)</i>")
        );

        // Dispersal
        // Each opponent returns a nonland permanent they control with the highest converted mana cost among permanents they control to its owner’s hand, then discards a card.
        this.getRightHalfCard().getSpellAbility().addEffect(new DispersalEffect());
    }

    private DiscoveryDispersal(final DiscoveryDispersal card) {
        super(card);
    }

    @Override
    public DiscoveryDispersal copy() {
        return new DiscoveryDispersal(this);
    }
}

class DispersalEffect extends OneShotEffect {

    DispersalEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent returns a nonland permanent "
                + "they control with the highest mana value "
                + "among permanents they control to its owner's hand, "
                + "then discards a card.";
    }

    private DispersalEffect(final DispersalEffect effect) {
        super(effect);
    }

    @Override
    public DispersalEffect copy() {
        return new DispersalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<PermanentIdPredicate> permsToReturn = new HashSet<>();
        for (UUID opponentId : game.getOpponents(player.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            int highestCMC = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponentId)) {
                if (permanent != null) {
                    highestCMC = Math.max(highestCMC, permanent.getManaValue());
                }
            }
            FilterPermanent filter = new FilterNonlandPermanent("permanent you control with mana value " + highestCMC);
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, highestCMC));
            filter.add(new ControllerIdPredicate(opponentId));
            Target target = new TargetPermanent(1, 1, filter, true);
            if (opponent.choose(outcome, target, source, game)) {
                if (target.getFirstTarget() == null) {
                    continue;
                }
                permsToReturn.add(new PermanentIdPredicate(target.getFirstTarget()));
            }
        }
        FilterPermanent filter = new FilterPermanent();
        filter.add(Predicates.or(permsToReturn));
        new ReturnToHandFromBattlefieldAllEffect(filter).apply(game, source);
        new DiscardEachPlayerEffect(
                StaticValue.get(1), false, TargetController.OPPONENT
        ).apply(game, source);
        return true;
    }
}
