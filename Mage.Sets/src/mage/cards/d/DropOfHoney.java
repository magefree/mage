
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author MarcoMarin, but I mostly copied from LevelX2's PorphyryNodes :)
 */
public final class DropOfHoney extends CardImpl {

    public DropOfHoney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // At the beginning of your upkeep, destroy the creature with the least power. It can't be regenerated. If two or more creatures are tied for least power, you choose one of them.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DropOfHoneyEffect(), TargetController.YOU, false));
        // When there are no creatures on the battlefield, sacrifice Drop of Honey.
        this.addAbility(new DropOfHoneyStateTriggeredAbility());
    }

    private DropOfHoney(final DropOfHoney card) {
        super(card);
    }

    @Override
    public DropOfHoney copy() {
        return new DropOfHoney(this);
    }
}

class DropOfHoneyEffect extends OneShotEffect {
    
    public DropOfHoneyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy the creature with the least power. It can't be regenerated. If two or more creatures are tied for least power, you choose one of them";
    }
    
    public DropOfHoneyEffect(final DropOfHoneyEffect effect) {
        super(effect);
    }
    
    @Override
    public DropOfHoneyEffect copy() {
        return new DropOfHoneyEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            int leastPower = Integer.MAX_VALUE;
            boolean multipleExist = false;
            Permanent permanentToDestroy = null;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), game)) {
                if (permanent.getPower().getValue() < leastPower) {
                    permanentToDestroy = permanent;
                    leastPower = permanent.getPower().getValue();
                    multipleExist = false;
                } else {
                    if (permanent.getPower().getValue() == leastPower) {
                        multipleExist = true;
                    }
                }
            }
            if (multipleExist) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("one of the creatures with the least power");
                filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, leastPower));
                Target target = new TargetPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(source.getControllerId(), source, game)) {
                    if (controller.choose(outcome, target, source, game)) {
                        permanentToDestroy = game.getPermanent(target.getFirstTarget());
                    }
                }
            }
            if (permanentToDestroy != null) {
                game.informPlayers(sourcePermanent.getName() + " chosen creature: " + permanentToDestroy.getName());
                return permanentToDestroy.destroy(source, game, true);
            }
            return true;
        }

        return false;
    }
}

class DropOfHoneyStateTriggeredAbility extends StateTriggeredAbility {

    public DropOfHoneyStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public DropOfHoneyStateTriggeredAbility(final DropOfHoneyStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DropOfHoneyStateTriggeredAbility copy() {
        return new DropOfHoneyStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().count(StaticFilters.FILTER_PERMANENT_CREATURES, this.getControllerId(), this, game) == 0;
    }

    @Override
    public String getTriggerPhrase() {
        return "When there are no creatures on the battlefield, " ;
    }
}