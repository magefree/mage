package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class BlatantThievery extends CardImpl {

    public BlatantThievery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}{U}");

        // For each opponent, gain control of target permanent that player controls.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true)
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, gain control of target permanent that player controls"));
        this.getSpellAbility().setTargetAdjuster(BlatantThieveryAdjuster.instance);
    }

    private BlatantThievery(final BlatantThievery card) {
        super(card);
    }

    @Override
    public BlatantThievery copy() {
        return new BlatantThievery(this);
    }
}

enum BlatantThieveryAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || game.getBattlefield().count(
                    StaticFilters.FILTER_CONTROLLED_PERMANENT,
                    opponentId, ability, game
            ) < 1) {
                continue;
            }
            FilterPermanent filter = new FilterPermanent("Permanent controlled by " + opponent.getName());
            filter.add(new ControllerIdPredicate(opponentId));
            TargetPermanent targetPermanent = new TargetPermanent(filter);
            ability.addTarget(targetPermanent);
        }
    }
}
