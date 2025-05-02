package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsDamageToThisAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class Tephraderm extends CardImpl {

    public Tephraderm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever a creature deals damage to Tephraderm, Tephraderm deals that much damage to that creature.
        this.addAbility(new DealsDamageToThisAllTriggeredAbility(
                new DamageTargetEffect(SavedDamageValue.MUCH)
                        .setText("{this} deals that much damage to that creature"),
                false, StaticFilters.FILTER_PERMANENT_CREATURE,
                SetTargetPointer.PERMANENT, false
        ));

        // Whenever a spell deals damage to Tephraderm, Tephraderm deals that much damage to that spell's controller.
        this.addAbility(new TephradermSpellDamageTriggeredAbility());
    }

    private Tephraderm(final Tephraderm card) {
        super(card);
    }

    @Override
    public Tephraderm copy() {
        return new Tephraderm(this);
    }
}

class TephradermSpellDamageTriggeredAbility extends TriggeredAbilityImpl {

    TephradermSpellDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(0));
    }

    private TephradermSpellDamageTriggeredAbility(final TephradermSpellDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }

        StackObject sourceSpell = game.getStack().getStackObject(event.getSourceId());
        if (sourceSpell != null && StaticFilters.FILTER_SPELL.match(sourceSpell, getControllerId(), this, game)) {
            for (Effect effect : getEffects()) {
                if (effect instanceof DamageTargetEffect) {
                    effect.setTargetPointer(new FixedTarget(sourceSpell.getControllerId()));
                    ((DamageTargetEffect) effect).setAmount(StaticValue.get(event.getAmount()));
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public TephradermSpellDamageTriggeredAbility copy() {
        return new TephradermSpellDamageTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a spell deals damage to {this}, {this} deals that much damage to that spell's controller.";
    }
}
