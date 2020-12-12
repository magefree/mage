
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class RustScarab extends CardImpl {

    public RustScarab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Rust Scarab becomes blocked, you may destroy target artifact or enchantment defending player controls.
        Effect effect = new DestroyTargetEffect();
        effect.setText("destroy target artifact or enchantment defending player controls");
        this.addAbility(new RustScarabBecomesBlockedTriggeredAbility(effect, true));

    }

    public RustScarab(final RustScarab card) {
        super(card);
    }

    @Override
    public RustScarab copy() {
        return new RustScarab(this);
    }
}


class RustScarabBecomesBlockedTriggeredAbility extends TriggeredAbilityImpl {

    public RustScarabBecomesBlockedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public RustScarabBecomesBlockedTriggeredAbility(final RustScarabBecomesBlockedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            UUID defenderId = game.getState().getCombat().findGroup(this.getSourceId()).getDefenderId();
            if (defenderId != null) {
                this.getTargets().clear();
                FilterPermanent filter = new FilterPermanent("artifact or enchantment defending player controls");
                filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
                filter.add(new ControllerIdPredicate(defenderId));
                Target target = new TargetPermanent(filter);
                this.addTarget(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes blocked, " + super.getRule();
    }

    @Override
    public RustScarabBecomesBlockedTriggeredAbility copy() {
        return new RustScarabBecomesBlockedTriggeredAbility(this);
    }
}
