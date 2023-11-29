package mage.cards.m;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 * @author TheElk801
 */
public final class MagusLuceaKane extends CardImpl {

    public MagusLuceaKane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TYRANID);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Spiritual Leader -- At the beginning of combat on your turn, put a +1/+1 counter on target creature.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Spiritual Leader"));

        // Psychic Stimulus -- {T}: Add {C}{C}. When you next cast a spell with {X} in its mana cost or activate an ability with {X} in its activation cost this turn, copy that spell or ability. You may choose new targets for the copy.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new MagusLuceaKaneTriggeredAbility()));
        this.addAbility(ability.withFlavorWord("Psychic Stimulus"));
    }

    private MagusLuceaKane(final MagusLuceaKane card) {
        super(card);
    }

    @Override
    public MagusLuceaKane copy() {
        return new MagusLuceaKane(this);
    }
}

class MagusLuceaKaneTriggeredAbility extends DelayedTriggeredAbility {

    MagusLuceaKaneTriggeredAbility() {
        super(new CopyStackObjectEffect(), Duration.EndOfTurn, true, false);
    }

    private MagusLuceaKaneTriggeredAbility(final MagusLuceaKaneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MagusLuceaKaneTriggeredAbility copy() {
        return new MagusLuceaKaneTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }

        // activated ability
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (stackAbility != null && !(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                if (stackAbility.getManaCostsToPay().containsX()) {
                    this.getEffects().setValue("stackObject", (StackObject) stackAbility);
                    return true;
                }
            }
        }

        // spell
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isInstantOrSorcery(game)) {
                if (spell.getSpellAbility().getManaCostsToPay().containsX()) {
                    this.getEffects().setValue("stackObject", (StackObject) spell);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you next cast a spell with {X} in its mana cost or activate an ability with {X} in its "
                + "activation cost this turn, copy that spell or ability. You may choose new targets for the copy.";
    }
}
