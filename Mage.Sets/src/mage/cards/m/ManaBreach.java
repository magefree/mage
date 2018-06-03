
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox

 */
public final class ManaBreach extends CardImpl {

    public ManaBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Whenever a player casts a spell, that player returns a land he or she controls to its owner's hand.
        this.addAbility(new SpellCastAllTriggeredAbility(new ManaBreachEffect(), StaticFilters.FILTER_SPELL, false, SetTargetPointer.PLAYER));
    }

    public ManaBreach(final ManaBreach card) {
        super(card);
    }

    @Override
    public ManaBreach copy() {
        return new ManaBreach(this);
    }
}

class ManaBreachEffect extends OneShotEffect {

    public ManaBreachEffect() {
        super(Outcome.Detriment);
        staticText="that player returns a land he or she controls to its owner's hand.";
    }

    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if(player != null) {
            FilterLandPermanent filter = new FilterLandPermanent("a land you control");
            filter.add(new ControllerIdPredicate(player.getId()));
            TargetLandPermanent toBounce = new TargetLandPermanent(1, 1, filter, true);
            if(player.chooseTarget(Outcome.ReturnToHand, toBounce, source, game)) {
                Permanent land = game.getPermanent(toBounce.getTargets().get(0));
                land.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }

    public ManaBreachEffect(final ManaBreachEffect effect) {
        super(effect);
    }

    public ManaBreachEffect copy() {
        return new ManaBreachEffect(this);
    }

}
