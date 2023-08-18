package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.replacement.AdditionalTriggerControlledETBReplacementEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class EleshNornMotherOfMachines extends CardImpl {

    public EleshNornMotherOfMachines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN, SubType.PRAETOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // If a permanent entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AdditionalTriggerControlledETBReplacementEffect()));

        // Permanents entering the battlefield don't cause abilities of permanents your opponents control to trigger.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EleshNornMotherOfMachinesPreventionEffect()));
    }

    private EleshNornMotherOfMachines(final EleshNornMotherOfMachines card) {super(card);}

    @Override
    public EleshNornMotherOfMachines copy() {return new EleshNornMotherOfMachines(this);}
}

class EleshNornMotherOfMachinesPreventionEffect extends ContinuousRuleModifyingEffectImpl {

    EleshNornMotherOfMachinesPreventionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "Permanents entering the battlefield don't cause abilities of permanents your opponents control to trigger";
    }

    EleshNornMotherOfMachinesPreventionEffect(final EleshNornMotherOfMachinesPreventionEffect effect) {
        super(effect);
    }

    @Override
    public EleshNornMotherOfMachinesPreventionEffect copy() {return new EleshNornMotherOfMachinesPreventionEffect(this);}

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Ability ability = (Ability) getValue("targetAbility");
        if(ability == null || ability.getAbilityType() != AbilityType.TRIGGERED) {
            return false;
        }

        // Elesh Norn should not prevent Bloodghast trigger from the graveyard.
        // This checks that the trigger originated from a permanent.
        if(ability.getSourcePermanentOrLKI(game) == null) {
            return false;
        }

        if (!game.getOpponents(source.getControllerId()).contains(ability.getControllerId())) {
            return false;
        }

        Permanent enteringPermanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (enteringPermanent == null) {
            return false;
        }

        return (((TriggeredAbility) ability).checkTrigger(event, game));
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject enteringObject = game.getObject(event.getSourceId());
        MageObject sourceObject = game.getObject(source);
        Ability ability = (Ability) getValue("targetAbility");
        if (enteringObject != null && sourceObject != null && ability != null) {
            MageObject abilitObject = game.getObject(ability.getSourceId());
            if (abilitObject != null) {
                return sourceObject.getLogName() + " prevented ability of " + abilitObject.getLogName()
                        + " to trigger for " + enteringObject.getLogName() + " entering the battlefield.";
            }
        }
        return null;
    }
}
