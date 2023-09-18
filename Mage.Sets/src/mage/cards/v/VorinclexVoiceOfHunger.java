package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class VorinclexVoiceOfHunger extends CardImpl {

    public VorinclexVoiceOfHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you tap a land for mana, add one mana of any type that land produced.
        ManaEffect effect = new AddManaOfAnyTypeProducedEffect();
        effect.setText("add one mana of any type that land produced");
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                effect, new FilterControlledLandPermanent("you tap a land"),
                SetTargetPointer.PERMANENT));

        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        this.addAbility(new VorinclexTriggeredAbility2());
    }

    private VorinclexVoiceOfHunger(final VorinclexVoiceOfHunger card) {
        super(card);
    }

    @Override
    public VorinclexVoiceOfHunger copy() {
        return new VorinclexVoiceOfHunger(this);
    }
}

class VorinclexTriggeredAbility2 extends TriggeredAbilityImpl {

    VorinclexTriggeredAbility2() {
        super(Zone.BATTLEFIELD, new DontUntapInControllersNextUntapStepTargetEffect());
    }

    private VorinclexTriggeredAbility2(final VorinclexTriggeredAbility2 ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // it's non mana triggered ability, so ignore it on checking, see TAPPED_FOR_MANA
        if (game.inCheckPlayableState()) {
            return false;
        }
        if (!game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        if (permanent == null || !permanent.isLand(game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }

    @Override
    public VorinclexTriggeredAbility2 copy() {
        return new VorinclexTriggeredAbility2(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.";
    }
}
