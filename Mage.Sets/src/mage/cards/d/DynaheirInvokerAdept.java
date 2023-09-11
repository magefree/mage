package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.watchers.common.ManaPaidSourceWatcher;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author Xanderhall
 */
public final class DynaheirInvokerAdept extends CardImpl {

    public DynaheirInvokerAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // You may activate abilities of other creatures you control as though those creatures had haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DynaheirInvokerAdeptHasteEffect()));

        // {T}: When you next activate an ability that isn't a mana ability this turn by spending four or more mana to activate it, copy that ability. You may choose new targets for the copy.
        this.addAbility(new SimpleActivatedAbility(new CreateDelayedTriggeredAbilityEffect(new DynaheirInvokerAdeptTriggeredAbility()), new TapSourceCost()));
        
    }

    private DynaheirInvokerAdept(final DynaheirInvokerAdept card) {
        super(card);
    }

    @Override
    public DynaheirInvokerAdept copy() {
        return new DynaheirInvokerAdept(this);
    }
}

class DynaheirInvokerAdeptHasteEffect extends AsThoughEffectImpl {

    public DynaheirInvokerAdeptHasteEffect() {
        super(AsThoughEffectType.ACTIVATE_HASTE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "you may activate abilities of other creatures you control as though those creatures had haste";
    }

    private DynaheirInvokerAdeptHasteEffect(final DynaheirInvokerAdeptHasteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DynaheirInvokerAdeptHasteEffect copy() {
        return new DynaheirInvokerAdeptHasteEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        return permanent != null
            && permanent.isCreature(game)
            && permanent.isControlledBy(source.getControllerId())
            && !permanent.getId().equals(source.getSourceId());
    }
}

class DynaheirInvokerAdeptTriggeredAbility extends DelayedTriggeredAbility {

    DynaheirInvokerAdeptTriggeredAbility() {
        super(new CopyStackObjectEffect(), Duration.EndOfTurn, true);
    }

    private DynaheirInvokerAdeptTriggeredAbility(final DynaheirInvokerAdeptTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DynaheirInvokerAdeptTriggeredAbility copy() {
        return new DynaheirInvokerAdeptTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null
                || stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl
                || ManaPaidSourceWatcher.getTotalPaid(stackAbility.getId(), game) < 4) {
            return false;
        }
        this.getEffects().setValue("stackObject", stackAbility);
        return true;
    }

    @Override
    public String getRule() {
        return "When you next activate an ability that isn't a mana ability this turn by spending four or more mana to activate it, " +
                "copy that ability. You may choose new targets for the copy.";
    }
}
