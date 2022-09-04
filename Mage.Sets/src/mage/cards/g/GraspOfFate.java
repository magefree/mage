package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class GraspOfFate extends CardImpl {

    public GraspOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // When Grasp of Fate enters the battlefield, for each opponent, exile up to one target nonland permanent that player controls until Grasp of Fate leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect("")
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, exile up to one target nonland permanent that player controls until {this} leaves the battlefield")
        );
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        ability.setTargetAdjuster(GraspOfFateAdjuster.instance);
        this.addAbility(ability);
    }

    private GraspOfFate(final GraspOfFate card) {
        super(card);
    }

    @Override
    public GraspOfFate copy() {
        return new GraspOfFate(this);
    }
}

enum GraspOfFateAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterPermanent("nonland permanent from opponent " + opponent.getLogName());
            filter.add(new ControllerIdPredicate(opponentId));
            filter.add(Predicates.not(CardType.LAND.getPredicate()));
            TargetPermanent target = new TargetPermanent(0, 1, filter, false);
            ability.addTarget(target);
        }
    }
}
