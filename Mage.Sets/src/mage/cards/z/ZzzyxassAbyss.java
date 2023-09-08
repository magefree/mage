
package mage.cards.z;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class ZzzyxassAbyss extends CardImpl {
    
    public ZzzyxassAbyss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");

        // At the beginning of your upkeep, destroy all nonland permanents with the first name alphabetically among nonland permanents.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ZzzyxassAbyssEffect(), TargetController.YOU, false));
    }

    private ZzzyxassAbyss(final ZzzyxassAbyss card) {
        super(card);
    }

    @Override
    public ZzzyxassAbyss copy() {
        return new ZzzyxassAbyss(this);
    }
    
}

class ZzzyxassAbyssEffect extends OneShotEffect {

    public ZzzyxassAbyssEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all nonland permanents with the first name alphabetically among nonland permanents";
    }

    private ZzzyxassAbyssEffect(final ZzzyxassAbyssEffect effect) {
        super(effect);
    }

    @Override
    public ZzzyxassAbyssEffect copy() {
        return new ZzzyxassAbyssEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<String> permanentNames = new ArrayList<>();
            FilterPermanent filter = new FilterNonlandPermanent();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, controller.getId(), game)) {
                permanentNames.add(permanent.getName());
            }
            if (permanentNames.isEmpty()) {
                return true;
            }
            Collections.sort(permanentNames);
            filter.add(new NamePredicate(permanentNames.get(0)));
            return new DestroyAllEffect(filter).apply(game, source);
        }
        return false;
    }
}
