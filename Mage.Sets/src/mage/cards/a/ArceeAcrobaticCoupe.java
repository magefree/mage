package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LivingMetalAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.Target;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArceeAcrobaticCoupe extends CardImpl {

    public ArceeAcrobaticCoupe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setRed(true);
        this.color.setWhite(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Whenever you cast a spell that targets one or more creatures or Vehicles you control, put that many +1/+1 counters on Arcee. Convert Arcee.
        this.addAbility(new ArceeAcrobaticCoupeTriggeredAbility());
    }

    private ArceeAcrobaticCoupe(final ArceeAcrobaticCoupe card) {
        super(card);
    }

    @Override
    public ArceeAcrobaticCoupe copy() {
        return new ArceeAcrobaticCoupe(this);
    }
}

class ArceeAcrobaticCoupeTriggeredAbility extends SpellCastControllerTriggeredAbility {

    ArceeAcrobaticCoupeTriggeredAbility() {
        super(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0),
                SavedDamageValue.MANY, false
        ), false);
        this.addEffect(new TransformSourceEffect());
    }

    private ArceeAcrobaticCoupeTriggeredAbility(final ArceeAcrobaticCoupeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArceeAcrobaticCoupeTriggeredAbility copy() {
        return new ArceeAcrobaticCoupeTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        int targets = spell
                .getStackAbility()
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isCreature(game)
                        || permanent.hasSubtype(SubType.VEHICLE, game))
                .map(Controllable::getControllerId)
                .map(this::isControlledBy)
                .mapToInt(x -> x ? 1 : 0)
                .sum();
        if (targets > 0) {
            this.getEffects().setValue("damage", targets);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell that targets one or more creatures or Vehicles " +
                "you control, put that many +1/+1 counters on {this}. Convert {this}.";
    }
}
