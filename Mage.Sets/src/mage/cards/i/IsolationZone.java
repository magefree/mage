
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public final class IsolationZone extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment an opponent controls");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public IsolationZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // When Isolation Zone enters the battlefield, exile target creature or enchantment an opponent controls until Isolation Zone leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new IsolationZoneExileEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private IsolationZone(final IsolationZone card) {
        super(card);
    }

    @Override
    public IsolationZone copy() {
        return new IsolationZone(this);
    }
}

class IsolationZoneExileEffect extends OneShotEffect {

    public IsolationZoneExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature or enchantment an opponent controls until {this} leaves the battlefield";
    }

    public IsolationZoneExileEffect(final IsolationZoneExileEffect effect) {
        super(effect);
    }

    @Override
    public IsolationZoneExileEffect copy() {
        return new IsolationZoneExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If Stasis Snare leaves the battlefield before its triggered ability resolves,
        // the target won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
