package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RienneAngelOfRebirth extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("multicolored creatures");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public RienneAngelOfRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other multicolored creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever another multicolored creature you control dies, return it to its owner's hand at the beginning of the next end step.
        this.addAbility(new RienneAngelOfRebirthTriggeredAbility());
    }

    private RienneAngelOfRebirth(final RienneAngelOfRebirth card) {
        super(card);
    }

    @Override
    public RienneAngelOfRebirth copy() {
        return new RienneAngelOfRebirth(this);
    }
}

class RienneAngelOfRebirthTriggeredAbility extends TriggeredAbilityImpl {

    RienneAngelOfRebirthTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RienneAngelOfRebirthEffect(), false);
    }

    private RienneAngelOfRebirthTriggeredAbility(final RienneAngelOfRebirthTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RienneAngelOfRebirthTriggeredAbility copy() {
        return new RienneAngelOfRebirthTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getTarget() == null || zEvent.getTarget().getId().equals(this.getSourceId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(zEvent.getTarget().getId());

        if (permanent != null
                && zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getFromZone() == Zone.BATTLEFIELD
                && permanent.isCreature()
                && permanent.getColor(game).isMulticolored()
                && permanent.isControlledBy(this.controllerId)) {
            this.getEffects().setTargetPointer(new FixedTarget(zEvent.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another multicolored creature you control dies, " +
                "return it to its owner's hand at the beginning of the next end step.";
    }
}

class RienneAngelOfRebirthEffect extends OneShotEffect {

    RienneAngelOfRebirthEffect() {
        super(Outcome.PutCardInPlay);
    }

    private RienneAngelOfRebirthEffect(final RienneAngelOfRebirthEffect effect) {
        super(effect);
    }

    @Override
    public RienneAngelOfRebirthEffect copy() {
        return new RienneAngelOfRebirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            Effect effect = new ReturnFromGraveyardToHandTargetEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
            effect.setText("return that card to your hand at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
            return true;
        }
        return false;
    }
}
