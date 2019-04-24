
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public final class GraspOfFate extends CardImpl {

    public GraspOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // When Grasp of Fate enters the battlefield, for each opponent, exile up to one target nonland permanent that player controls until Grasp of Fate leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GraspOfFateExileEffect());
        ability.addTarget(new TargetPermanent());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    public GraspOfFate(final GraspOfFate card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            ability.getTargets().clear();
            for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterPermanent filter = new FilterPermanent("nonland permanent from opponent " + opponent.getLogName());
                    filter.add(new ControllerIdPredicate(opponentId));
                    filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
                    TargetPermanent target = new TargetPermanent(0, 1, filter, false);
                    ability.addTarget(target);
                }
            }
        }
    }

    @Override
    public GraspOfFate copy() {
        return new GraspOfFate(this);
    }
}

class GraspOfFateExileEffect extends OneShotEffect {

    public GraspOfFateExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each opponent, exile up to one target nonland permanent that player controls until {this} leaves the battlefield";
    }

    public GraspOfFateExileEffect(final GraspOfFateExileEffect effect) {
        super(effect);
    }

    @Override
    public GraspOfFateExileEffect copy() {
        return new GraspOfFateExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) { // 11/4/2015: If Grasp of Fate leaves the battlefield before its triggered ability resolves, no nonland permanents will be exiled.
            return new ConditionalOneShotEffect(new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName(), Zone.BATTLEFIELD, true), SourceOnBattlefieldCondition.instance).apply(game, source);
        }
        return false;
    }
}
