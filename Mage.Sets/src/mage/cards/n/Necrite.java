
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author MarcoMarin
 */
public final class Necrite extends CardImpl {

    public Necrite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Necrite attacks and isn't blocked, you may sacrifice it. If you do, destroy target creature defending player controls. It can't be regenerated.
        DoIfCostPaid effect = new DoIfCostPaid(new DestroyTargetEffect(true), new SacrificeSourceCost(), "Sacrifice {this} to destroy target creature defending player controls?");
        effect.setText("you may sacrifice it. If you do, destroy target creature defending player controls. It can't be regenerated.");
        Ability ability = new NecriteTriggeredAbility(effect);
        this.addAbility(ability);
        
    }

    private Necrite(final Necrite card) {
        super(card);
    }

    @Override
    public Necrite copy() {
        return new Necrite(this);
    }
}

class NecriteTriggeredAbility extends AttacksAndIsNotBlockedTriggeredAbility{
    
    public NecriteTriggeredAbility(Effect effect) {
        super(effect);
    }

    private NecriteTriggeredAbility(final NecriteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NecriteTriggeredAbility copy() {
        return new NecriteTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)){
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
            FilterPermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(defendingPlayerId));
            Target target = new TargetPermanent(filter);
            this.getTargets().clear();
            this.addTarget(target);
            return true;
        }
        return false;
    }
}