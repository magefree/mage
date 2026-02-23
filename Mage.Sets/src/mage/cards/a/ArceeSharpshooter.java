package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArceeSharpshooter extends TransformingDoubleFacedCard {

    public ArceeSharpshooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{1}{R}{W}",
                "Arcee, Acrobatic Coupe",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "RW");

        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(2, 2);

        // More Than Meets the Eye {R}{W}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{R}{W}"));

        // First strike
        this.getLeftHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // {1}, Remove one or more +1/+1 counters from Arcee: It deals that much damage to target creature. Convert Arcee.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(GetXValue.instance)
                        .setText("it deals that much damage to target creature"),
                new GenericManaCost(1)
        );
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.P1P1, 1));
        ability.addEffect(new TransformSourceEffect().setText("convert {this}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Arcee, Acrobatic Coupe
        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Whenever you cast a spell that targets one or more creatures or Vehicles you control, put that many +1/+1 counters on Arcee. Convert Arcee.
        this.getRightHalfCard().addAbility(new ArceeAcrobaticCoupeTriggeredAbility());
    }

    private ArceeSharpshooter(final ArceeSharpshooter card) {
        super(card);
    }

    @Override
    public ArceeSharpshooter copy() {
        return new ArceeSharpshooter(this);
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
