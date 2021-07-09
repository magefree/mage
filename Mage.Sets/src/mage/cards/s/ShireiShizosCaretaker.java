package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ShireiShizosCaretaker extends CardImpl {

    public ShireiShizosCaretaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature with power 1 or less is put into your graveyard from the battlefield, you may return that card to the battlefield at the beginning of the next end step if Shirei, Shizo's Caretaker is still on the battlefield.
        this.addAbility(new ShireiShizosCaretakerTriggeredAbility(this.getId()));
    }

    private ShireiShizosCaretaker(final ShireiShizosCaretaker card) {
        super(card);
    }

    @Override
    public ShireiShizosCaretaker copy() {
        return new ShireiShizosCaretaker(this);
    }
}

class ShireiShizosCaretakerTriggeredAbility extends TriggeredAbilityImpl {

    ShireiShizosCaretakerTriggeredAbility(UUID shireiId) {
        super(Zone.BATTLEFIELD, new ShireiShizosCaretakerEffect(shireiId), false);
    }

    ShireiShizosCaretakerTriggeredAbility(final ShireiShizosCaretakerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShireiShizosCaretakerTriggeredAbility copy() {
        return new ShireiShizosCaretakerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent LKIpermanent = game.getPermanentOrLKIBattlefield(zEvent.getTargetId());
        Card card = game.getCard(zEvent.getTargetId());
        if (card != null
                && LKIpermanent != null
                && card.isOwnedBy(this.controllerId)
                && zEvent.isDiesEvent()
                && card.isCreature(game)
                && LKIpermanent.getPower().getValue() <= 1) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(zEvent.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature with power 1 or less is put into your graveyard from the battlefield, you may return that card to the battlefield at the beginning of the next end step if Shirei, Shizo's Caretaker is still on the battlefield.";
    }
}

class ShireiShizosCaretakerEffect extends OneShotEffect {

    protected final UUID shireiId;

    ShireiShizosCaretakerEffect(UUID shireiId) {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may return that card to the battlefield at the beginning of the next end step if {this} is still on the battlefield.";
        this.shireiId = shireiId;
    }

    ShireiShizosCaretakerEffect(final ShireiShizosCaretakerEffect effect) {
        super(effect);
        this.shireiId = effect.shireiId;
    }

    @Override
    public ShireiShizosCaretakerEffect copy() {
        return new ShireiShizosCaretakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ShireiShizosCaretakerReturnEffect(shireiId);
            effect.setText("return that card to the battlefield if {this} is still on the battlefield");
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(card, game));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class ShireiShizosCaretakerReturnEffect extends ReturnToBattlefieldUnderYourControlTargetEffect {

    protected final UUID shireiId;

    ShireiShizosCaretakerReturnEffect(UUID shireiId) {
        this.shireiId = shireiId;
    }

    ShireiShizosCaretakerReturnEffect(final ShireiShizosCaretakerReturnEffect effect) {
        super(effect);
        this.shireiId = effect.shireiId;
    }

    @Override
    public ShireiShizosCaretakerReturnEffect copy() {
        return new ShireiShizosCaretakerReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().containsPermanent(shireiId)) {
            return super.apply(game, source);
        }
        return false;
    }
}
