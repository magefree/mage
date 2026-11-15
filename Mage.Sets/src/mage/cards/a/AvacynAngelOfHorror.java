package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class AvacynAngelOfHorror extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TokenPredicate.FALSE);
    }

    public AvacynAngelOfHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Avacyn or another nontoken creature you control dies, return that card to the battlefield under your control at the beginning of the next end step.
        this.addAbility(new AvacynAngelOfHorrorTriggeredAbility());
    }

    private AvacynAngelOfHorror(final AvacynAngelOfHorror card) {
        super(card);
    }

    @Override
    public AvacynAngelOfHorror copy() {
        return new AvacynAngelOfHorror(this);
    }
}

class AvacynAngelOfHorrorTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TokenPredicate.FALSE);
    }

    AvacynAngelOfHorrorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AvacynAngelOfHorrorEffect());
        setTriggerPhrase("Whenever {this} or another nontoken creature you control dies, ");
        setLeavesTheBattlefieldTrigger(true);
    }

    private AvacynAngelOfHorrorTriggeredAbility(final AvacynAngelOfHorrorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AvacynAngelOfHorrorTriggeredAbility copy() {
        return new AvacynAngelOfHorrorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent() || !zEvent.isPermanentMoved()) {
            return false;
        }

        Permanent permanent = zEvent.getTarget();
        if (permanent == null || !permanent.isControlledBy(this.getControllerId()) || !permanent.isCreature(game) || permanent.isToken()) {
            return false;
        }

        if (!permanent.getId().equals(this.getSourceId()) && !filter.match(permanent, this.getControllerId(), this, game)) {
            return false;
        }

        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game) + 1));
        }
        return true;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}

class AvacynAngelOfHorrorEffect extends OneShotEffect {

    AvacynAngelOfHorrorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return that card to the battlefield under your control at the beginning of the next end step";
    }

    private AvacynAngelOfHorrorEffect(final AvacynAngelOfHorrorEffect effect) {
        super(effect);
    }

    @Override
    public AvacynAngelOfHorrorEffect copy() {
        return new AvacynAngelOfHorrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
        effect.setText("return that card to the battlefield under your control at the beginning of the next end step");
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        delayedAbility.getEffects().get(0).setTargetPointer(this.getTargetPointer().copy());
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}
