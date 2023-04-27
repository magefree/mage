package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class LyndeCheerfulTormentor extends CardImpl {

    public LyndeCheerfulTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a Curse is put into your graveyard from the battlefield, return it to the battlefield attached to you at the beginning of the next end step.
        this.addAbility(new LyndeCheerfulTormentorCurseDiesTriggeredAbility());

        // At the beginning of your upkeep, you may attach a Curse attached to you to one of your opponents. If you do, draw two cards.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LyndeCheerfulTormentorAttachCurseEffect(), TargetController.YOU, true));
    }

    private LyndeCheerfulTormentor(final LyndeCheerfulTormentor card) {
        super(card);
    }

    @Override
    public LyndeCheerfulTormentor copy() {
        return new LyndeCheerfulTormentor(this);
    }
}

class LyndeCheerfulTormentorCurseDiesTriggeredAbility extends TriggeredAbilityImpl {

    public LyndeCheerfulTormentorCurseDiesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new LyndeCheerfulTormentorReturnCurseEffect()
                )
        ));
    }

    private LyndeCheerfulTormentorCurseDiesTriggeredAbility(final LyndeCheerfulTormentorCurseDiesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LyndeCheerfulTormentorCurseDiesTriggeredAbility copy() {
        return new LyndeCheerfulTormentorCurseDiesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = zEvent.getTarget();
            if (permanent != null && permanent.hasSubtype(SubType.CURSE, game) && permanent.isOwnedBy(controllerId)) {
                getEffects().setValue("curse", zEvent.getTargetId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }

    @Override
    public String getRule() {
        return "Whenever a Curse is put into your graveyard from the battlefield, return it to the battlefield attached to you at the beginning of the next end step.";
    }
}

class LyndeCheerfulTormentorReturnCurseEffect extends OneShotEffect {

    public LyndeCheerfulTormentorReturnCurseEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield attached to you at the beginning of the next end step";
    }

    private LyndeCheerfulTormentorReturnCurseEffect(final LyndeCheerfulTormentorReturnCurseEffect effect) {
        super(effect);
    }

    @Override
    public LyndeCheerfulTormentorReturnCurseEffect copy() {
        return new LyndeCheerfulTormentorReturnCurseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card curse = game.getCard((UUID)getValue("curse"));
        if (controller != null && curse != null) {
            game.getState().setValue("attachTo:" + curse.getId(), controller.getId());
            controller.moveCards(curse, Zone.BATTLEFIELD, source, game);
            controller.addAttachment(curse.getId(), source, game);
            return true;
        }
        return false;
    }
}

class LyndeCheerfulTormentorAttachCurseEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CURSE, "Curse attached to you");

    static {
        filter.add(LyndeCheerfulTormentorPredicate.instance);
    }

    public LyndeCheerfulTormentorAttachCurseEffect() {
        super(Outcome.Benefit);
        staticText = "attach a Curse attached to you to one of your opponents. If you do, draw two cards";
    }

    private LyndeCheerfulTormentorAttachCurseEffect(final LyndeCheerfulTormentorAttachCurseEffect effect) {
        super(effect);
    }

    @Override
    public LyndeCheerfulTormentorAttachCurseEffect copy() {
        return new LyndeCheerfulTormentorAttachCurseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetPermanent targetPermanent = new TargetPermanent(filter);
            targetPermanent.setNotTarget(true);
            if (controller.chooseTarget(outcome, targetPermanent, source, game)) {
                Permanent curse = game.getPermanent(targetPermanent.getFirstTarget());
                if (curse != null) {
                    TargetOpponent targetOpponent = new TargetOpponent(true);
                    if (controller.chooseTarget(Outcome.Detriment, targetOpponent, source, game)) {
                        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
                        if (opponent != null) {
                            controller.removeAttachment(curse, source, game);
                            opponent.addAttachment(curse.getId(), source, game);

                            game.informPlayers(
                                    curse.getLogName() + " is now attached to " + opponent.getLogName() + ".");
                            controller.drawCards(2, source, game);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

enum LyndeCheerfulTormentorPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = input.getObject();
        return permanent != null && permanent.isAttachedTo(input.getPlayerId());
    }

    @Override
    public String toString() {
        return "attached to you";
    }
}
