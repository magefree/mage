
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastFromGraveyardWatcher;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PrizedAmalgam extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public PrizedAmalgam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature enters the battlefield, if it entered from your graveyard or you cast it from your graveyard, return Prized Amalgam from your graveyard to the battlefield tapped at the beginning of the next end step.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect(true);
        effect.setText("return {this} from your graveyard to the battlefield tapped at the beginning of the next end step");
        this.addAbility(new PrizedAmalgamTriggerdAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect)), filter),
                new CastFromGraveyardWatcher());
    }

    private PrizedAmalgam(final PrizedAmalgam card) {
        super(card);
    }

    @Override
    public PrizedAmalgam copy() {
        return new PrizedAmalgam(this);
    }
}

class PrizedAmalgamTriggerdAbility extends EntersBattlefieldAllTriggeredAbility {

    PrizedAmalgamTriggerdAbility(Effect effect, FilterPermanent filter) {
        super(Zone.GRAVEYARD, effect, filter, false);
    }

    private PrizedAmalgamTriggerdAbility(PrizedAmalgamTriggerdAbility ability) {
        super(ability);
    }

    @Override
    public PrizedAmalgamTriggerdAbility copy() {
        return new PrizedAmalgamTriggerdAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        /**
         * 4/8/2016 Prized Amalgam's ability triggers only if it's in your
         * graveyard immediately after a creature enters the battlefield from
         * your graveyard or you cast a creature from your graveyard. A Prized
         * Amalgam that's already on the battlefield won't be returned at the
         * beginning of the next end step if it's put into your graveyard later.
         */
        boolean result = false;
        if (super.checkTrigger(event, game)) {
            EntersTheBattlefieldEvent entersEvent = (EntersTheBattlefieldEvent) event;
            if (entersEvent.getFromZone() == Zone.GRAVEYARD) {
                result = true;
            } else if (entersEvent.getFromZone() == Zone.STACK && entersEvent.getTarget().isControlledBy(getControllerId())) {
                CastFromGraveyardWatcher watcher = game.getState().getWatcher(CastFromGraveyardWatcher.class);
                if (watcher != null) {
                    int zcc = game.getState().getZoneChangeCounter(event.getSourceId());
                    result = watcher.spellWasCastFromGraveyard(event.getSourceId(), zcc - 1);
                }
            }
        }
        if (result) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(getSourceId(), game.getState().getZoneChangeCounter(getSourceId())));
            }
        }
        return result;
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield, if it entered from your graveyard or you cast it from your graveyard, return {this} from your graveyard to the battlefield tapped at the beginning of the next end step.";
    }

}
