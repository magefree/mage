
package mage.cards.p;

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
 * @author LevelX2
 */
public final class PorphyryNodes extends CardImpl {
    
    static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    
    public PorphyryNodes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");


        // At the beginning of your upkeep, destroy the creature with the least power. It can't be regenerated. If two or more creatures are tied for least power, you choose one of them.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PorphyryNodesEffect(), TargetController.YOU, false));

        // When there are no creatures on the battlefield, sacrifice Porphyry Nodes.
        this.addAbility(new PorphyryNodesStateTriggeredAbility());
        
    }

    private PorphyryNodes(final PorphyryNodes card) {
        super(card);
    }

    @Override
    public PorphyryNodes copy() {
        return new PorphyryNodes(this);
    }
}

class PorphyryNodesEffect extends OneShotEffect {
    
    public PorphyryNodesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy the creature with the least power. It can't be regenerated. If two or more creatures are tied for least power, you choose one of them";
    }
    
    public PorphyryNodesEffect(final PorphyryNodesEffect effect) {
        super(effect);
    }
    
    @Override
    public PorphyryNodesEffect copy() {
        return new PorphyryNodesEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            int leastPower = Integer.MAX_VALUE;
            boolean multipleExist = false;
            Permanent permanentToDestroy = null;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(PorphyryNodes.filter, source.getControllerId(), game)) {
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

class PorphyryNodesStateTriggeredAbility extends StateTriggeredAbility {

    public PorphyryNodesStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
        setTriggerPhrase("When there are no creatures on the battlefield, " );
    }

    public PorphyryNodesStateTriggeredAbility(final PorphyryNodesStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PorphyryNodesStateTriggeredAbility copy() {
        return new PorphyryNodesStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().count(PorphyryNodes.filter, this.getControllerId(), this, game) == 0;
    }
}
