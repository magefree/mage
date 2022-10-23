package mage.cards.v;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttachedToCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.effects.common.counter.RemoveCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author L_J
 */
public final class VenarianGold extends CardImpl {

    public VenarianGold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Venarian Gold enters the battlefield, tap enchanted creature and put X sleep counters on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect());
        ability.addEffect(new AddCountersAttachedEffect(CounterType.SLEEP.createInstance(), new VenarianGoldValue(), "it").setText("and put X sleep counters on it"));
        this.addAbility(ability);

        // Enchanted creature doesn’t untap during its controller’s untap step if it has a sleep counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousRuleModifyingEffect(new DontUntapInControllersUntapStepEnchantedEffect(),
                new AttachedToCounterCondition(CounterType.SLEEP, 1)).setText("Enchanted creature doesn't untap during its controller's untap step if it has a sleep counter on it")));

        // At the beginning of the upkeep of enchanted creature’s controller, remove a sleep counter from that creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RemoveCountersAttachedEffect(CounterType.SLEEP.createInstance(), "that creature"),
                TargetController.CONTROLLER_ATTACHED_TO, false));

    }

    private VenarianGold(final VenarianGold card) {
        super(card);
    }

    @Override
    public VenarianGold copy() {
        return new VenarianGold(this);
    }
}

class VenarianGoldValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        MageObject mageObject = game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.STACK);
        if (mageObject instanceof StackObject) {
            return ((StackObject) mageObject).getStackAbility().getManaCostsToPay().getX();
        }
        return 0;
    }

    @Override
    public VenarianGoldValue copy() {
        return new VenarianGoldValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
