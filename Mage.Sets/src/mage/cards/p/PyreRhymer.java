package mage.cards.p;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.mana.DelayedTriggeredManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author notshauna
 */

public final class PyreRhymer extends PrepareCard {

    public PyreRhymer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}", "Molten Tide", new CardType[]{CardType.INSTANT}, "{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //Prowess
        this.addAbility(new ProwessAbility());

        //This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Molten Tide
        // Instant {R}
        // Until end of turn, whenever you tap a Mountain for mana, add an additional {R}.
        this.getSpellCard().getSpellAbility().addEffect(
                new CreateDelayedTriggeredAbilityEffect(new MoltenTideTriggeredAbility()));
    }

    private PyreRhymer(final PyreRhymer card) {
        super(card);
    }

    @Override
    public PyreRhymer copy() {
        return new PyreRhymer(this);
    }
}

class MoltenTideTriggeredAbility extends DelayedTriggeredManaAbility {

    MoltenTideTriggeredAbility() {
        super(new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.R), "your"), Duration.EndOfTurn, false);
        this.usesStack = false;
    }

    private MoltenTideTriggeredAbility(MoltenTideTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = ((TappedForManaEvent) event).getPermanent();
        if (land == null || !land.hasSubtype(SubType.MOUNTAIN, game) || !land.isControlledBy(getControllerId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(land.getControllerId()));
        return true;
    }

    @Override
    public MoltenTideTriggeredAbility copy() {
        return new MoltenTideTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever you tap a Mountain for mana, add an additional {R}.";
    }
}